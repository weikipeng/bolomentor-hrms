package com.unknown.wiki.constant;
//import com
public interface Constant_SQL{
	
	public final String SQL_INSERT = "insert into ";

	public final String SQL_UPDATE = "update ";
	
	public final String SQL_SET = " set ";

	public final String SQL_QUERY = "select * from ";
	
//	public final String SQL_INSERT_COMPANY = "insert into " + Constant_Table.TABLE_COMPANY + " set ";

	public final String SQL_AND = " and ";

	public final String SQL_WHERE = " where ";

	public final String SQL_OPEN_BRACE = "{";
	public final String SQL_CLOSE_BRACE = "}";

	public final String SQL_LEFT_BRACKET = "[";
	public final String SQL_RIGHT_BRACKET  = "]";
	
	public final String SQL_OPEN_PARENTHESIS = "(";
	public final String SQL_CLOSE_PARENTHESIS = ")";

	public final String SQL_EQ = " = ";

	public final String SQL_IN = " in ";

	public final String SQL_QUESTIONMARK = "?";
	
	public final String SQL_SINGLE_QUOTES = "'";
	
	public final String SQL_SEMICOLON = ";";
	
	public final String SQL_COMMA = ",";
	
	
//	while (iterator.hasNext()) {
//		String key = iterator.next();
//		if(count != 0){
//			sb.append(" and ");
//		}else{
//			sb.append(" where ");
//		}
//		sb.append(key);
//		sb.append(SQL_EQ);sb.append(SQL_SINGLE_QUOTES);
//		sb.append(parameters.getString(key));
//		sb.append("'");
//		count++;
//	}
//	
//	if(!Constant_Servlet.ROLE_ADMIN.equals(role)){
//		if(sb.indexOf(" where ")>0){
//			sb.append(" and ");
//		}else{
//			sb.append(" where ");
//		}
//		
//		sb.append(COLUMN_CREATEUSERID);
//		sb.append(SQL_EQ);sb.append(SQL_SINGLE_QUOTES);
//		sb.append(userId);
//		sb.append("'");
//	}
//	
//	sb.append(";");
}
