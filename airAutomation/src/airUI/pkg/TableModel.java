package airUI.pkg;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    
    
    String[] colNamesDaily = {"Time", "O2",  "CO2", "Humidity","Temperature"};
    String[] colNamesWeekly = {"Week", "O2",  "CO2", "Humidity","Temperature"};
    String[] colNames = {"Month", "O2",  "CO2", "Humidity","Temperature"};
    String[] colNamesYearly = {"Year", "O2",  "CO2", "Humidity","Temperature"};
    
    
    Object[][] data = {
    {"", "", "", " ", " "},
    {"", "", "", "", ""},
    
    };
    
    
	public int getColumnCount() {
		return colNames.length;
	}
    
	public int getRowCount() {
		return data.length;
	}
    
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
        
	}
	public String getColumnName(int index) {
        return colNames[index];
    }
	
	public void setData(String[] colNames, Object[][] data){
		this.colNames = colNames;
		this.data = data;
		
		fireTableDataChanged();
		fireTableStructureChanged();
	}
    
	public void addAverage(Object[] row) {
		data[data.length-1] = row;
		
	}
	
    
}