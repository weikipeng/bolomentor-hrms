package com.unknown.wiki.constant;
//import com
public interface Constant_SQL{
	
	public final String SQL_INSERTPROJECT = "insert into ";

	public final String SQL_QUERY = "select * from ";
	
	public final String SQL_INSERT_COMPANY = "insert into " + Constant_Table.TABLE_COMPANY + " set ";

	public final String SQL_AND = " and ";

	public final String SQL_WHERE = " where ";

	public final String SQL_EQ = " = ";
	
	public final String SQL_SINGLE_QUOTES = "'";
	
	public final String SQL_SEMICOLON = ";";
	
	
//	while (iterator.hasNext()) {
//		String key = iterator.next();
//		if(count != 0){
//			sb.append(" and ");
//		}else{
//			sb.append(" where ");
//		}
//		sb.append(key);
//		sb.append("= '");
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
//		sb.append("= '");
//		sb.append(userId);
//		sb.append("'");
//	}
//	
//	sb.append(";");
}
