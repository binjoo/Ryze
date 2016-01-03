package com.base.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.base.utils.CoreMap;
import com.base.utils.StrUtils;

/**
 * @author binjoo
 * @description 数据库SQL语句构建类
 * @date 2014-11-20
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DBQuery {
	/** 插入操作 */
	public final static int INSERT = 0x01;
	/** 删除操作 */
	public final static int DELETE = 0x02;
	/** 更新操作 */
	public final static int UPDATE = 0x03;
	/** 查询操作 */
	public final static int SELECT = 0x04;

	/** 表内连接查询 */
	public final static String INNER_JOIN = "inner";
	/** 表外连接查询 */
	public final static String OUTER_JOIN = "outer";
	/** 表左连接查询 */
	public final static String LEFT_JOIN = "left";
	/** 表右连接查询 */
	public final static String RIGHT_JOIN = "right";
	/** 表全连接查询 */
	public final static String FULL_JOIN = "full";
	/** 升序查询 */
	public final static String SORT_ASC = "asc";
	/** 降序查询 */
	public final static String SORT_DESC = "desc";

	private String sql;

	private Object[] params;
	private Object[] wheres;

	private int action;
	private String fields;
	private String table;
	private List<String[]> join;
	private String where;
	private String limit;
	private String offset;
	private String order;
	private String group;
	private String having;
	private CoreMap rows;
	private String primary;

	public DBQuery() {
		this.sql = "";
		// this.params = new Object[0];
		this.wheres = new Object[0];
		this.action = 0;
		this.fields = "*";
		this.join = new ArrayList();
		this.where = "";
		this.limit = "";
		this.offset = "";
		this.order = "";
		this.group = "";
		this.having = "";
		this.rows = new CoreMap();
		this.primary = "id";
	}
	
	public String getTable() {
		return this.table;
	}

	public Object[] getParams() {
		return this.params;
	}

	private String getColumnFromParameters(Object[] params) {
		String[] fields = null;

		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			if (param instanceof String[]) {
				String[] as = (String[]) param;
				fields = (String[]) ArrayUtils.add(fields, as[0] + " as " + as[1]);
			} else if (param instanceof String) {
				String field = (String) param;
				fields = (String[]) ArrayUtils.add(fields, field);
			} else {
				throw new IllegalArgumentException("field 参数类型错误。");
			}
		}

		return StrUtils.implode(" , ", fields);
	}

	public DBQuery insert() {
		this.action = DBQuery.INSERT;
		return this;
	}

	public DBQuery delete() {
		this.action = DBQuery.DELETE;
		return this;
	}

	public DBQuery update() {
		this.action = DBQuery.UPDATE;
		return this;
	}

	public DBQuery select(Object... fields) {
		this.action = DBQuery.SELECT;
		if (fields == null || fields.length == 0) {
			this.fields = "*";
		} else {
			this.fields = getColumnFromParameters(fields);
		}
		return this;
	}

	public DBQuery from(String table) {
		this.table = table;
		return this;
	}

	public DBQuery join(String table, String condition) {
		return join(table, condition, null);
	}

	public DBQuery join(String table, String condition, String op) {
		if (op == null || op.equals("")) {
			op = DBQuery.LEFT_JOIN;
		}
		String[] join = { table, condition, op };
		this.join.add(join);
		return this;
	}

	/**
	 * 排序（order by asc）
	 * 
	 * @param orderby
	 * @param sort
	 * @return
	 */
	public DBQuery order(String orderby) {
		return order(orderby, null);
	}

	/**
	 * 排序（order by）
	 * 
	 * @param orderby
	 * @param sort
	 * @return
	 */
	public DBQuery order(String orderby, String sort) {
		if (sort == null || sort.equals("")) {
			sort = DBQuery.SORT_ASC;
		}
		if (this.order != null && !this.order.equals("")) {
			this.order += ", " + orderby + " " + sort;
		} else {
			this.order = " order by " + orderby + " " + sort;
		}
		return this;
	}

	/**
	 * 分组函数
	 * 
	 * @param group
	 * @return
	 */
	public DBQuery group(String group) {
		this.group = " group by " + group;
		return this;
	}

	public DBQuery limit(int limit) {
		this.limit = " limit " + limit;
		return this;
	}

	public DBQuery offset(int offset) {
		this.offset = " offset " + offset;
		return this;
	}

	public DBQuery page(int page, int size) {
		this.limit = " limit " + size;
		this.offset = " offset " + (((page > 0 ? page : 1) - 1) * size);
		return this;
	}

	public DBQuery primary(String primary) {
		this.primary = primary;
		return this;
	}

	public DBQuery where(Object... params) {
		if (params == null) {
			return this;
		}
		String condition = (String) params[0];
		if (this.where != null && !this.where.equals("")) {
			this.where += " and" + " (" + condition + ")";
		} else {
			this.where += " where" + " (" + condition + ")";
		}
		if (params.length > 1) {
			params = ArrayUtils.remove(params, 0);
			this.wheres = ArrayUtils.addAll(this.wheres, params);
		}
		return this;
	}

	public DBQuery rows(CoreMap rows) {
		this.rows.putAll(rows);
		return this;
	}

	/**
	 * 生成插入SQL
	 * 
	 * @return
	 */
	private String buildInsert() {
		StringBuffer out = new StringBuffer("insert into ");
		out.append(this.table);
		Object[] k = new String[0];
		Object[] v = new String[0];
		Iterator it = rows.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			Object key = e.getKey();
			Object value = e.getValue();
			if (value == null) {
				continue;
			}
			k = ArrayUtils.add(k, key);
			v = ArrayUtils.add(v, "?");
			this.params = ArrayUtils.add(this.params, value);
		}
		out.append(" (");
		out.append(this.primary == null ? "" : this.primary + ", ");
		out.append(StrUtils.implode(" , ", k) + ") ");
		out.append("values");
		out.append(" (");
		out.append(this.primary == null ? "" : "'{{PRIMARY}}', ");
		out.append(StrUtils.implode(" , ", v) + ") ");
		return out.toString();
	}

	/**
	 * 生成删除SQL
	 * 
	 * @return
	 */
	private String buildDelete() {
		StringBuffer out = new StringBuffer("delete from ");
		out.append(this.table);
		out.append(this.where);
		if (this.wheres.length > 0) {
			for (int i = 0; i < this.wheres.length; i++) {
				this.params = ArrayUtils.add(this.params, this.wheres[i]);
			}
		}
		return out.toString();
	}

	/**
	 * 生成更新SQL
	 * 
	 * @return
	 */
	private String buildUpdate() {
		StringBuffer out = new StringBuffer("update ");
		Object[] set = new String[0];
		Iterator it = rows.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			Object key = e.getKey();
			Object value = e.getValue();
			if (value == null) {
				if(key.toString().indexOf("=") > -1){
					set = ArrayUtils.add(set, key);
				}
				continue;
			}
			set = ArrayUtils.add(set, key + " = ?");
			this.params = ArrayUtils.add(this.params, value);
		}
		out.append(this.table);
		out.append(" set ");
		out.append(StrUtils.implode(" , ", set));
		out.append(this.where);
		if (this.wheres.length > 0) {
			for (int i = 0; i < this.wheres.length; i++) {
				this.params = ArrayUtils.add(this.params, this.wheres[i]);
			}
		}
		return out.toString();
	}

	/**
	 * 生成数量统计SQL语句
	 * 
	 * @return
	 */
	public String buildCount() {
		this.params = new Object[0];
		StringBuffer out = new StringBuffer("select count(*) as stat from ( select ");
		String _table = this.table;
		out.append(this.fields + " from ");
		if (this.join.size() > 0) {
			for (int i = 0; i < this.join.size(); i++) {
				String[] join = this.join.get(i);
				_table += " " + join[2] + " join " + join[0] + " on " + join[1];
			}
		}
		out.append(_table);
		out.append(this.where);
		if (this.wheres.length > 0) {
			for (int i = 0; i < this.wheres.length; i++) {
				this.params = ArrayUtils.add(this.params, this.wheres[i]);
			}
		}
		out.append(this.group);
		out.append(" ) t");
		return out.toString();
	}

	private String buildSelect() {
		StringBuffer out = new StringBuffer("select ");
		String _table = this.table;
		out.append(this.fields + " from ");
		if (this.join.size() > 0) {
			for (int i = 0; i < this.join.size(); i++) {
				String[] join = this.join.get(i);
				_table += " " + join[2] + " join " + join[0] + " on " + join[1];
			}
		}
		out.append(_table);
		out.append(this.where);
		if (this.wheres.length > 0) {
			for (int i = 0; i < this.wheres.length; i++) {
				this.params = ArrayUtils.add(this.params, this.wheres[i]);
			}
		}
		out.append(this.group);
		out.append(this.order);
		out.append(this.limit);
		out.append(this.offset);
		return out.toString();
	}

	/**
	 * 生成最终SQL语句
	 * 
	 * @return
	 */
	public String build() {
		this.params = new Object[0];
		switch (this.action) {
		case DBQuery.INSERT:
			this.sql = buildInsert();
			break;
		case DBQuery.DELETE:
			this.sql = buildDelete();
			break;
		case DBQuery.UPDATE:
			this.sql = buildUpdate();
			break;
		case DBQuery.SELECT:
			this.sql = buildSelect();
			break;
		default:
			this.sql = null;
			break;
		}
		return this.sql;
	}

	public static void main(String[] args) {
		DBQuery q = new DBQuery();
		q.select("t1.id", "t1.name", "t2.remark").from("table_name t1");
		q.join("table_name t2", "t2.t1_id = t1.id");
		q.join("table_name t3", "t3.t2_id = t2.id", DBQuery.LEFT_JOIN);
		q.order("xx");
		System.out.println(q.build());
		q.select("count(*) as stat");
		System.out.println(q.build());
		if(q.getParams().length >= 1){
			System.out.print("> `Params: `{ ");
			for (int i = 0; i < q.params.length; i++) {
				if(i != 0){
					System.out.print(", ");
				}
				Object param = q.params[i];
				if (param instanceof String) {
					System.out.print("\"" + String.valueOf(param) + "\"");
				} else if (param instanceof Integer) {
					System.out.print(Integer.valueOf(String.valueOf(param)));
				}
			}
			System.out.print(" }");
		}
	}
}