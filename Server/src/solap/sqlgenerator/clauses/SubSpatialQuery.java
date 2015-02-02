package solap.sqlgenerator.clauses;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import solap.params.SOLAPSpatialSliceParams;

import solap.sqlgenerator.clauses.whereclauses.SpatialSliceWhereClause;
import solap.sqlgenerator.clauses.whereclauses.WhereClause;

import solap.utils.MappingUtils;


public class SubSpatialQuery {
    private List<FromClause> fromClauses;
    
    //Store the mapping the table and her alias
    private Map<String, String> aliasTable;
    private List<WhereClause> whereClauses;

    private MappingUtils mapUtils;
    private String tableName;
    
    //ASCII CODE FOR: a
    private int caracter = 97;
    
    public SubSpatialQuery(MappingUtils mapUtils, SOLAPSpatialSliceParams spatialSlice) {
        aliasTable = new HashMap<String, String>();
        fromClauses = new LinkedList<FromClause>();
        whereClauses = new LinkedList<WhereClause>();
        
        this.mapUtils = mapUtils;
        
        String tableId = mapUtils.getTableId(spatialSlice.getLevelId(), spatialSlice.getDimensionId());
        tableName = mapUtils.getTableName(tableId);
        
        aliasTable.put(tableName, (char)caracter+ "");
        addFromClause(new FromClause(tableName, (char)caracter+ ""));
        caracter++;
        
        addLayer(spatialSlice.getLayerId());
        addSpatialSliceClause(spatialSlice);
    }
    
    //If this is function is called we know the spatial slice is perform to the same dimension
    public void addNewSpatialSlice(SOLAPSpatialSliceParams spatialSlice) {
        addLayer(spatialSlice.getLayerId());
        addSpatialSliceClause(spatialSlice);
    }
    
    private void addLayer(String layerId) {
        String layerTable = mapUtils.getLayerTableName(layerId);
        String aliasLayer = (char)caracter+ "";
        aliasTable.put(layerTable, aliasLayer);
        caracter++;
        
        addFromClause(new FromClause(layerTable, aliasLayer));
    }
    
    private void addSpatialSliceClause(SOLAPSpatialSliceParams spatialSlice) {
        addWhereClause(new SpatialSliceWhereClause(mapUtils, aliasTable, spatialSlice));
    }
    
    private void addFromClause(FromClause fromClause) {
        if(!fromClauses.contains(fromClause)) fromClauses.add(fromClause);
    }
    
    private void addWhereClause(WhereClause whereClause) {
        if(!whereClauses.contains(whereClause)) whereClauses.add(whereClause);
    }
    
    public String toSQL() {
        String sql = "SELECT " + aliasTable.get(tableName) + ".*";
        
        sql += " FROM ";
        for(FromClause clause: fromClauses)
            sql += clause.toSQLWithAlias() + ",";
        sql = sql.substring(0, sql.length() - 1);
        
        sql += " WHERE ";
        for(WhereClause clause: whereClauses)
            sql += clause.toSQL() + " AND ";        
        sql = sql.substring(0, sql.length() - 4);
        
        return "(" + sql + ") " + tableName;
    }
    
    public String getTableName() {
        return tableName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SubSpatialQuery)) {
            return false;
        }
        final SubSpatialQuery other = (SubSpatialQuery)object;
        if (!(tableName == null ? other.tableName == null : tableName.equals(other.tableName))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((tableName == null) ? 0 : tableName.hashCode());
        return result;
    }
}
