package solap.sqlgenerator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import solap.entities.SOLAPFactTable;
import solap.entities.SumarizationAttribute;
import solap.entities.SumarizationMeasure;

import solap.params.SOLAPAttributeParams;
import solap.params.SOLAPFieldFilterParams;
import solap.params.SOLAPLevelParams;
import solap.params.SOLAPMeasureParams;
import solap.params.SOLAPSliceParams;
import solap.params.SOLAPSpatialSliceParams;
import solap.sqlgenerator.clauses.FromClause;
import solap.sqlgenerator.clauses.GroupByClause;
import solap.sqlgenerator.clauses.HavingClause;
import solap.sqlgenerator.clauses.OrderByClause;
import solap.sqlgenerator.clauses.selectclauses.AttributeSelectClause;
import solap.sqlgenerator.clauses.selectclauses.IntersectionSelectClause;
import solap.sqlgenerator.clauses.selectclauses.MeasureSelectClause;
import solap.sqlgenerator.clauses.selectclauses.SelectClause;
import solap.sqlgenerator.clauses.selectclauses.SpatialSelectClause;
import solap.sqlgenerator.clauses.whereclauses.MeasurePreFilterWhereClause;
import solap.sqlgenerator.clauses.whereclauses.RestrictionWhereClause;
import solap.sqlgenerator.clauses.whereclauses.SliceWhereClause;
import solap.sqlgenerator.clauses.whereclauses.WhereClause;
import solap.sqlgenerator.clauses.SubSpatialQuery;
import solap.utils.MappingUtils;

public class Query implements IQuery {
    
    public static final String EMPTY = "empty";

    private List<SelectClause> selectClauses;
    private List<FromClause> fromClauses;
    private List<SubSpatialQuery> subSpatialQueries;
    private List<WhereClause> whereClauses;
    private List<GroupByClause> groupByClauses;    
    private List<HavingClause> havingClauses;
    private List<OrderByClause> orderByClauses;
    
    private MappingUtils mapUtils;
    private String factTableName;
    
    private boolean clausesGenerated;
    
    private List<SpatialSelectClause> spatialObjects;
    private List<AttributeSelectClause> spatialSemantic;
    
    public Query(MappingUtils mapUtils) throws Exception {
        this.selectClauses = new LinkedList<SelectClause>();
        this.fromClauses = new LinkedList<FromClause>();
        this.subSpatialQueries = new LinkedList<SubSpatialQuery>();
        this.whereClauses = new LinkedList<WhereClause>();
        this.groupByClauses = new LinkedList<GroupByClause>();    
        this.havingClauses = new LinkedList<HavingClause>();
        this.orderByClauses = new LinkedList<OrderByClause>();
        this.factTableName = "";
        this.mapUtils = mapUtils;
        this.spatialObjects = new LinkedList<SpatialSelectClause>();
        this.spatialSemantic = new LinkedList<AttributeSelectClause>();
    }
    
    public Query(MappingUtils mapUtils, String factTableName) throws Exception {
        this.selectClauses = new LinkedList<SelectClause>();
        this.fromClauses = new LinkedList<FromClause>();
        this.subSpatialQueries = new LinkedList<SubSpatialQuery>();
        this.whereClauses = new LinkedList<WhereClause>();
        this.groupByClauses = new LinkedList<GroupByClause>();    
        this.havingClauses = new LinkedList<HavingClause>();
        this.orderByClauses = new LinkedList<OrderByClause>();
        this.factTableName = "";
        this.mapUtils = mapUtils;
        this.spatialObjects = new LinkedList<SpatialSelectClause>();
        this.spatialSemantic = new LinkedList<AttributeSelectClause>();
        this.factTableName = factTableName;        
    }

    public String buildSemanticQuery() {
        selfGenerateClauses();
        
        String result = "SELECT ";
        result += generateHint();
        
        Iterator<SelectClause> itSelect = selectClauses.iterator();
        while(itSelect.hasNext()) {
            SelectClause next = itSelect.next();
            if(next instanceof AttributeSelectClause || next instanceof MeasureSelectClause) {
                result += next.toSQL();
                result += ",";
            }
        }
        
        result = result.substring(0, result.length()-1);
        result += buildFromClauses();
        result += buildSubQueries();
        result += buildWhereClauses();
        result += buildGroupByClauses();
        result += buildOrderByClauses();
        
        return result;
    }
    
    public String buildSpatialQuery() {
        selfGenerateClauses();
        
        String result = "SELECT ";
        result += generateHint();
        
        Iterator<SelectClause> itSelect = selectClauses.iterator();
        while(itSelect.hasNext()) {
            SelectClause next = itSelect.next();
            result += next.toSQL();
            result += ",";
        }
        
        result = result.substring(0, result.length()-1);
        result += buildFromClauses();
        result += buildSubQueries();
        result += buildWhereClauses();
        result += buildGroupByClauses();
        result += buildOrderByClauses();
        
        System.out.println("QUERY ESPACIAL: " + result);
        
        return result;
    }
    
    public String buildSpatialQueryByIndex(int index, String levelId, String dimensionId, Vector<SOLAPAttributeParams> higherAttr) {
        //If we have higher attributes we need to put them in query to restrict the clustering by them
        List<SelectClause> higherClauses = new LinkedList<SelectClause>();
        for(SOLAPAttributeParams attr: higherAttr) higherClauses.add(new AttributeSelectClause(mapUtils, attr.getId(), attr.getLevelId(), attr.getDimensionId()));
        selfGenerateClauses();
        
        //Build the query
        String result = "SELECT ";
        
        result += spatialSemantic.get(index).toSQL() + ",";
        result += spatialObjects.get(index).toSQL();
        
        if(!levelId.equals("")) result += genNewSelectBasedOnLevel(levelId, dimensionId);
        
        for(SelectClause clause: higherClauses) result += "," + clause.toSQL();
        
        result += buildFromClauses();
        
        if(!levelId.equals("")) result += genNewFromtBasedOnLevel(levelId, dimensionId);          
        
        result += buildSubQueries();
        result += buildWhereClauses();
        
        if(!levelId.equals("")) result += genNewWhereClauseBasedOnLevel(levelId, dimensionId);
        
        result += " GROUP BY ";
        
        for(String groupByClause: spatialObjects.get(index).getGroupBy()) result += groupByClause + ",";
        for(SelectClause clause: higherClauses) result += clause.getGroupBy().get(0) + ",";
            
        result = result.substring(0, result.length()-1);
        
        if(!levelId.equals("")) {
            SOLAPLevelParams level = new SOLAPLevelParams();
            level.setId(levelId);
            level.setDimensionId(dimensionId);
            result += "," + new AttributeSelectClause(mapUtils, level).getGroupBy().get(0);
        }
        
        System.out.println("QUERY PELO O INDICE DO OBJECTO: " + result);
        return result;
    }
    
    public String buildDistinctQuery() {
        selfGenerateClauses();
        
        String result = "SELECT ";
        
        Iterator<SelectClause> itSelect = selectClauses.iterator();
        while(itSelect.hasNext()) {
            SelectClause next = itSelect.next();
            if(next instanceof AttributeSelectClause) {
                ((AttributeSelectClause)next).setDistinct(true);
                result += next.toSQL();
                result += ",";
            }
        }
        result = result.substring(0, result.length()-1);
        
        result += buildFromClauses();
        result += "," + factTableName;
        result += buildSubQueries();
        result += buildWhereClauses();
        result += buildOrderByClauses();
        
        return result;
    }

    public void addMeasure(SOLAPMeasureParams measure, String factTableName) {
        this.factTableName = factTableName;
        addSelectClause(new MeasureSelectClause(mapUtils, measure, factTableName));
    }
    
    public void addLevels(Vector<SOLAPLevelParams> levels) {
        boolean sameDimension = sameDimension(levels);
        
        //Intersection 
        if(sameDimension) {
            addSelectClause(new IntersectionSelectClause(mapUtils, levels));
        }
            
        for(SOLAPLevelParams level: levels) {
            SpatialSelectClause temp = new SpatialSelectClause(mapUtils, level);
            AttributeSelectClause temp2 = new AttributeSelectClause(mapUtils, level);
            
            addSelectClause(temp);
            addSelectClause(temp2);
            
            spatialObjects.add(temp);
            spatialSemantic.add(temp2);
        }
    }
    
    public void addAttribute(SOLAPAttributeParams attr) {
        addSelectClause(new AttributeSelectClause(mapUtils, attr.getId(), attr.getLevelId(), attr.getDimensionId()));
    }
    
    public void addSlice(SOLAPSliceParams slice) {
        addFromClause(new FromClause(mapUtils, slice.getLevelId(), slice.getDimensionId()));
        
        if(isSnowFlaked(slice.getLevelId(), slice.getDimensionId())) {
            RestrictionWhereClause temp = new RestrictionWhereClause(mapUtils,slice.getLevelId() , slice.getDimensionId(), "snowflaked");
            addWhereClause(temp);
            addFromClause(new FromClause(temp.getTable1()));
            addFromClause(new FromClause(temp.getTable2()));
        }
        
        addWhereClause(new RestrictionWhereClause(mapUtils, slice.getDimensionId() , factTableName));
        addWhereClause(new SliceWhereClause(mapUtils, slice.getAttributeId(), slice.getLevelId(), slice.getDimensionId()), slice.getValue1());
    }
    
    public void addSpatialSlice(SOLAPSpatialSliceParams spatialSlice) {
        SubSpatialQuery subQuery = new SubSpatialQuery(mapUtils, spatialSlice);
        SubSpatialQuery temp = containsSpatialSlice(subQuery);
        
        if(temp == null)  subSpatialQueries.add(subQuery);
        else temp.addNewSpatialSlice(spatialSlice);
    }
    
    public void addMeasurePreFilter(SOLAPFieldFilterParams measurePreFilter) {
        addWhereClause(new MeasurePreFilterWhereClause(mapUtils, measurePreFilter, factTableName));
    }
    
    public String getColumnTypeSpatial(int index) {
        if(index >= spatialObjects.size())
            return null;
        
        return mapUtils.getAttributeColumnType(spatialObjects.get(index).getAttrId());
    }

    public void centroidOperation(int index) {
        SelectClause clause = spatialObjects.get(index);
        ((SpatialSelectClause)clause).setSpatialCentroid(true);
    }

    // Based on select clauses generate from and where clauses
    // This clauses are responsible for cartesian product
    private void selfGenFromClauses() {
        for(SelectClause clause: selectClauses) {
            if(clause instanceof MeasureSelectClause) addFromClause(new FromClause(clause.getTable()));
                
            else if(!(clause instanceof IntersectionSelectClause)) {
                //Dimension snowflaked
                if(isSnowFlaked(clause.getLevelId(), clause.getDimensionId())) addFromClause(new FromClause(mapUtils, clause.getDimensionId()));
                addFromClause(new FromClause(clause.getTable()));
            }
        }
    }
    
    private void selfGenJoinRestrictions() {
        String factTable = "";
        
        for(SelectClause clause: selectClauses) {
            
            if(clause instanceof MeasureSelectClause)
                factTable = clause.getTable();
            else if(!(clause instanceof IntersectionSelectClause)) {
                System.out.println("Level id:" + clause.getLevelId() + " dimension id: "+  clause.getDimensionId());
                if(isSnowFlaked(clause.getLevelId(), clause.getDimensionId())){
                    System.out.println("SOU SNOWFLAKED");
                    addWhereClause(new RestrictionWhereClause(mapUtils, clause.getLevelId(), clause.getDimensionId(), "snowflaked"));
                }
                
                //Give priority to the existence of aggregate
                if(factTable.equals(""))
                    factTable = factTableName;
                System.out.println("Cheguei aqui 1. Fact table: " + factTable);
                addWhereClause(new RestrictionWhereClause(mapUtils, clause.getDimensionId() , factTable));
                System.out.println("Cheguei aqui 2");
            }
        }
    }
    
    private void selfGenGroupByClauses() {
        for(SelectClause clause: selectClauses) {
            if(!(clause instanceof MeasureSelectClause)) {
                for(String groupByClause: clause.getGroupBy())
                    addGroupByClause(new GroupByClause(groupByClause));
            }
        }
    }
    
    private void selfGenOrderByClauses() {
        for(SelectClause clause: selectClauses) {
            if(clause instanceof AttributeSelectClause) {
                addOrderByClause(new OrderByClause(mapUtils, ((AttributeSelectClause)clause).getAttrId(), clause.getLevelId(), clause.getDimensionId()));
            }
        }
    }
    
    private boolean isSnowFlaked(String levelId, String dimensionId) {
        String tableRef = mapUtils.getTableId2(levelId, dimensionId);
        return !tableRef.equals("");
    }
    
    private void addSelectClause(SelectClause selectClause) {
        if(!selectClauses.contains(selectClause)) selectClauses.add(selectClause);
    }
    
    private void addFromClause(FromClause fromClause) {
        if(!fromClauses.contains(fromClause)) fromClauses.add(fromClause);
    }
    
    private void addWhereClause(WhereClause whereClause) {
        if(!whereClauses.contains(whereClause)) whereClauses.add(whereClause);
    }
    
    private void addWhereClause(SliceWhereClause whereClause, String value) {
        boolean add = true;
        
        for(WhereClause clause: whereClauses) {
            
            if(clause instanceof SliceWhereClause) {
                if(clause.equals(whereClause)) {
                    ((SliceWhereClause)clause).addValue(value);
                    add = false;
                    break;
                }
            }
        }
        
        if(add) {
            whereClauses.add(whereClause);
            whereClause.addValue(value);
        }
    }
    
    private void addGroupByClause(GroupByClause groupByClause) {
        if(!groupByClauses.contains(groupByClause)) groupByClauses.add(groupByClause);
    }
    
    private void addOrderByClause(OrderByClause orderByClause) {
        if(!orderByClauses.contains(orderByClause)) orderByClauses.add(orderByClause);
    }
    
    private boolean sameDimension(Vector<SOLAPLevelParams> levels) {
        if(levels.size() <= 1)
            return false;
        
        SOLAPLevelParams firstLevel = levels.get(0);
        SOLAPLevelParams secondLevel = levels.get(1);
        
        return firstLevel.getDimensionId().equals(secondLevel.getDimensionId());
    }
    
    private String buildFromClauses() {
        String result = " FROM ";
        Iterator<FromClause> itFrom = fromClauses.iterator();
        while(itFrom.hasNext()) {
            FromClause next = itFrom.next();
            result += next.toSQL();
            result += ",";
        }
        return result.substring(0, result.length()-1);
    }
    
    private String buildSubQueries() {
        if(subSpatialQueries.size() > 0) {
            String result = " , ";
            Iterator<SubSpatialQuery> itSubQuery = subSpatialQueries.iterator();
            while(itSubQuery.hasNext()) {
                SubSpatialQuery next = itSubQuery.next();
                result += next.toSQL();
                result += ",";
            }
            return result.substring(0, result.length()-1);
        }
        return "";
    }
    
    private String buildWhereClauses() {
        String result = " WHERE ";
        Iterator<WhereClause> itWhere = whereClauses.iterator();
        while(itWhere.hasNext()) {
            WhereClause next = itWhere.next();
            result += next.toSQL();
            result += " AND ";
        }
        return result.substring(0, result.length()-5);
    }
    
    private String buildGroupByClauses() {
        String result = " GROUP BY ";
        Iterator<GroupByClause> itGroup = groupByClauses.iterator();
        while(itGroup.hasNext()) {
            GroupByClause next = itGroup.next();
            result += next.toSQL();
            result += ",";
        }
        return result.substring(0, result.length()-1);
    }
    
    private String buildOrderByClauses() {
        String result = " ORDER BY ";
        Iterator<OrderByClause> itOrder = orderByClauses.iterator();
        while(itOrder.hasNext()) {
            OrderByClause next = itOrder.next();
            result += next.toSQL();
            result += ",";
        }
        return result.substring(0, result.length()-1);
    }
    
    private void selfGenerateClauses() {
        System.out.println("SELF GENERATE CLAUSES");
        if(!clausesGenerated) {
            selfGenFromClauses();
            System.out.println("GEN FROM CLAUSES");
            selfGenJoinRestrictions();
            System.out.println("GEN JOIN RESTRITIONS CLAUSES");
            selfGenGroupByClauses();
            System.out.println("GEN GROUP BY CLAUSES");
            selfGenOrderByClauses();
            System.out.println("GEN ORDER CLAUSES");
            remAmbiguoslyColumn();
            clausesGenerated = true;
        }
    }
    
    private void remAmbiguoslyColumn() {
        for(SubSpatialQuery subQuery: subSpatialQueries) {
            Iterator<FromClause> itFrom = fromClauses.iterator();
            while(itFrom.hasNext()) {
                FromClause next = itFrom.next();
                System.out.println("Table: " + subQuery.getTableName() + " == " + next.getTable());
                if(next.getTable().equals(subQuery.getTableName())) {
                    itFrom.remove();
                    break;
                }
            }        
        }
    }
    
    private SubSpatialQuery containsSpatialSlice(SubSpatialQuery subQuery) {
        for(SubSpatialQuery query: subSpatialQueries) {
            if(query.equals(subQuery))
                return query;
        }
        
        return null;
    }
    
    private String genNewSelectBasedOnLevel(String levelId, String dimensionId) {
        String result ="";
        SOLAPLevelParams level = new SOLAPLevelParams();
        level.setId(levelId);
        level.setDimensionId(dimensionId);
        
        AttributeSelectClause higherLevel = new AttributeSelectClause(mapUtils, level);
        result += "," + higherLevel.toSQL();
        
        return result;
    }
    
    private String genNewFromtBasedOnLevel(String levelId, String dimensionId) {
        String result = "";
        boolean add = true;
        
        FromClause temp = new FromClause(mapUtils,levelId, dimensionId);
        for(FromClause clause : fromClauses)
            if(clause.equals(temp))
                add = false;
        if(add) result+= "," + temp.getTable();
        
        return result;
    }
    
    private String genNewWhereClauseBasedOnLevel(String levelId, String dimensionId) {
        String result = "";
        
        boolean add = true;
        WhereClause temp = null;
        
        System.out.println("SNOWFLAKED: " + isSnowFlaked(levelId, dimensionId));
        System.out.println(levelId + " ---- " +  dimensionId);
        if(isSnowFlaked(levelId, dimensionId))
            temp = new RestrictionWhereClause(mapUtils, levelId, dimensionId, "snowflaked");
        
        if(temp != null) {
            for(WhereClause clause : whereClauses)
                if(clause.equals(temp))
                    add = false;
            
            System.out.println("ADD: " + add + " clause: " + temp.toSQL());
            if(add) result+= " AND " + temp.toSQL();
        }
        
        return result;
    }
    
    private String generateHint() {
        if(subSpatialQueries.size() > 0)
            return "/* NO_MERGE(" + subSpatialQueries.get(0).getTableName() + ") */ ";
                
        return "";
    }
    
    //To Summarization
    
    public String buildFocusSummarizationQuery(){
        selfGenerateClauses();
        String query = "SELECT ";
        
        Iterator<SelectClause> itSelect = selectClauses.iterator();
        while(itSelect.hasNext()) {
            SelectClause next = itSelect.next();
            if(next instanceof AttributeSelectClause || next instanceof MeasureSelectClause) {
                query += next.toSQL();
                query += ",";
            }
        }
        query = query.substring(0, query.length()-1);
        query += buildFromClauses();
        query += buildWhereClauses();
        query += buildOrderByClauses();
        
        return query;
    }
    
    public String buildFocusDistinctSummarizationQuery(){
        selfGenerateClauses();
        String query = "SELECT DISTINCT ";
        
        Iterator<SelectClause> itSelect = selectClauses.iterator();
        while(itSelect.hasNext()) {
            SelectClause next = itSelect.next();
            if(next instanceof AttributeSelectClause || next instanceof MeasureSelectClause) {
                query += next.toSQL();
                query += ",";
            }
        }
        query = query.substring(0, query.length()-1);
        query += buildFromClauses();
        query += buildWhereClauses();
        query += buildOrderByClauses();
        
        return query;
    }
    
    public void addFactTable(SOLAPFactTable ft){
        this.factTableName = ft.getName();
        addFromClause(new FromClause(factTableName));
    }
    
    public void addSummarizationAttribute(SumarizationAttribute att){
        System.out.println("NAME: " + att.getName());
        addSelectClause(new AttributeSelectClause(mapUtils, att.getAttId(), att.getLevelId(), att.getDimensionId()));
        System.out.println("ADICIONEI ESTE-> ATTID: " + att.getAttId() + " LEVELID: " + att.getLevelId() + " DIMENSIONID: " + att.getDimensionId());
        if(!att.getAttributeToId().equals("")){
            System.out.println("ENTREI AQUI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            String attId = att.getAttributeToId();
            String levelId = mapUtils.getLevelByAttribute(attId);
            String dimensionId = mapUtils.getDimensionByLevel(levelId);
            addSelectClause(new AttributeSelectClause(mapUtils,attId,levelId,dimensionId));
        }
    }
    
    public void addSummarizationMeasure(SumarizationMeasure m){
        addSelectClause(new MeasureSelectClause(mapUtils, m, factTableName));
    }

}
