package com.common.action;

import java.io.IOException;
import java.util.List;

import org.pegdown.Parser;
import org.pegdown.PegDownProcessor;

import com.base.action.CoreAction;
import com.base.db.DBQuery;
import com.base.utils.ActionUtils;
import com.base.utils.Constants;
import com.base.utils.CoreMap;
import com.base.utils.DateUtils;
import com.base.utils.StrUtils;
import com.common.dao.BookmarkDao;
import com.common.dao.GroupDao;
import com.common.dao.NoteDao;

@SuppressWarnings("rawtypes")
public class NoteAction extends CoreAction {
	NoteDao noteDao = new NoteDao();

	public CoreMap index(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if (!isLogin()) {
			return goToLogin();
		}

    	DBQuery q = new DBQuery();
    	q.select().from("note_content");
		if(inMap.containsKey("parent_id")){
			out.put("parent_id", inMap.getString("parent_id"));
	    	q.where("parent_id = ?", inMap.getString("parent_id"));
		}else{
	    	q.where("parent_id = ?", "0");
		}
    	q.where("user_id = ?", this.getLoginInfo().getString("id"));
    	q.order("type", DBQuery.SORT_DESC).order("created", DBQuery.SORT_ASC);
    	NoteDao ntoeDao = new NoteDao();
	    List list = ntoeDao.queryList(q);

		out.put("breadcrumb", ActionUtils.makeBreadcrumb("记事本"));
		out.put("list", list);
		out.setOutRender("/note/index");
		return out;
	}
	
	
    public CoreMap notFound(CoreMap inMap) throws Exception {
        CoreMap out = new CoreMap();
        if(this.getParts().length >= 2 && StrUtils.isInt(this.getParts()[1])){
        	out = this.view(inMap);
        }else{
        	out.setOutType(Constants.OUT_TYPE__REDIRECT);
        }
        return out;
    }

	private CoreMap view(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		String id = this.getParts()[1];
		DBQuery q = new DBQuery();
		q.select().from("note_content").where("id = ?", id);
		CoreMap note = noteDao.querySingle(q);
		if(note != null && note.getString("syntax").equals("1")){
            String content = new PegDownProcessor().markdownToHtml(note.getString("content"));
			note.put("content", content);
		}

		out.put("breadcrumb", ActionUtils.makeBreadcrumb("记事本", "/note", "查看笔记 - " + note.getString("title")));
		out.put("note", note);
		out.setOutRender("/note/view");
		return out;
	}

	public CoreMap add(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if (!isLogin()) {
			return goToLogin();
		}
		if (isGet()) {
			out = this.add_Get(inMap);
		} else if (isPost()) {
			out = this.edit_Post(inMap);
		}
		return out;
	}

	private CoreMap add_Get(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		CoreMap tmp = new CoreMap();
		tmp.put("user_id", getLoginInfo().getInt("id"));
		
		List folder = this.queryFolder(null);

		out.put("breadcrumb", ActionUtils.makeBreadcrumb("记事本", "/note", "新建笔记"));
		out.put("folder", folder);
		out.setOutRender("/note/edit_note");
		return out;
	}

	public CoreMap folder_edit(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if (!isLogin()) {
			return goToLogin();
		}
		if (isGet()) {
			out = this.folder_edit_Get(inMap);
		} else if (isPost()) {
			out = this.folder_edit_Post(inMap);
		}
		return out;
	}

	private CoreMap folder_edit_Get(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		CoreMap tmp = new CoreMap();
		tmp.put("user_id", getLoginInfo().getInt("id"));
		List list = new BookmarkDao().query(tmp);

		out.put("breadcrumb", ActionUtils.makeBreadcrumb("记事本", "/note", "创建文件夹"));
		out.put("list", list);
		out.setOutRender("/note/edit_folder");
		return out;
	}

	private CoreMap folder_edit_Post(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		inMap.put("type", "1");
		inMap.put("parent_id", "0");
		inMap.put("user_id", getLoginInfo().getInt("id"));
		int id = noteDao.update(inMap);

		out.setOutType(Constants.OUT_TYPE__REDIRECT);
		out.setOutRender("/note/index");
		return out;
	}

	/**
	 * 编辑记事
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	public CoreMap edit(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if (!isLogin()) {
			return goToLogin();
		}
		if (isGet()) {
			out = this.edit_Get(inMap);
		} else if (isPost()) {
			out = this.edit_Post(inMap);
		}
		return out;
	}

	private CoreMap edit_Get(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
        if(this.getParts().length < 4 && !StrUtils.isInt(this.getParts()[2])){
			out.setOutType(Constants.OUT_TYPE__REDIRECT);
			out.setOutRender("/note");
			return out;
		}
		String id = this.getParts()[2];
		DBQuery q = new DBQuery();
		q.select().from("note_content").where("id = ?", id);
		CoreMap note = noteDao.querySingle(q);
		
		List folder = this.queryFolder(null);

		out.put("breadcrumb", ActionUtils.makeBreadcrumb("记事本", "/note", "编辑笔记"));
		out.put("note", note);
		out.put("folder", folder);
		out.setOutRender("/note/edit_note");
		return out;
	}

	private CoreMap edit_Post(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		
    	CoreMap rows = new CoreMap();
    	rows.put("parent_id", inMap.getString("parent_id"));
    	rows.put("user_id", inMap.getString("user_id"));
    	rows.put("type", inMap.getString("type"));
    	rows.put("title", inMap.getString("title"));
    	rows.put("content", inMap.getString("content"));
    	rows.put("syntax", inMap.getString("syntax"));
    	rows.put("status", inMap.getString("status"));
    	rows.put("udpated", DateUtils.getTime());
    	
    	DBQuery query = new DBQuery().from("note_content");

    	int resultId = 0;
    	if(inMap.getString("id") != null && !inMap.getString("id").equals("")){
        	rows.put("modifyed = modifyed + 1", null);
    		resultId = inMap.getInt("id");
    		query.rows(rows).update().where("id = ?", resultId);
        	//DBHepler.update(query.build(), query.getParams());
        	noteDao.update(query);
    	}else{
        	rows.put("type", "0");
    		rows.put("user_id", getLoginInfo().getInt("id"));
        	rows.put("created", DateUtils.getTime());
    		query.rows(rows).insert();
    		//id = DBHepler.insert(query.build(), query.getParams());
    		resultId = noteDao.insert(query);
    	}
    	
    	

		out.put("breadcrumb", ActionUtils.makeBreadcrumb("记事本", "/note", "查看笔记"));
		out.setOutType(Constants.OUT_TYPE__REDIRECT);
		out.setOutRender("/note/" + resultId);
		return out;
	}
	
	public CoreMap folder(CoreMap inMap) throws Exception {
		CoreMap out = new CoreMap();
		if(this.getParts().length == 3){
			if(StrUtils.isInt(this.getParts()[2])){
				//文件夹列表
				String parent_id = this.getParts()[2];
				inMap.put("parent_id", parent_id);
				out = this.index(inMap);
			}else{
				out = this.folder_edit(inMap);
			}
		}else if(this.getParts().length == 4){
			
		}
		return out;
	}
	
	private List queryFolder(CoreMap inMap) throws Exception {
    	DBQuery q = new DBQuery();
    	q.select().from("note_content");
    	q.where("type = ?", "1");
    	q.where("user_id = ?", this.getLoginInfo().getString("id"));
    	q.order("created", DBQuery.SORT_ASC);
    	GroupDao gd = new GroupDao();
	    List list = gd.queryList(q);
	    return list;
	}
	
	public static void main(String[] args) {
	    StringBuffer sb = new StringBuffer("### 你好" +
	    		"");
        sb.append("111|222");
        sb.append("\n");
        sb.append("---|---");
        sb.append("\n");
        sb.append("a|b");
        sb.append("\n");
        sb.append("c|d");
        sb.append("\n");
        sb.append("~~Mistaken text.~~");
        sb.append("\n");
        sb.append("> Pardon my french");
        sb.append("\n");
        sb.append("[Visit GitHub!](www.github.com)");
        System.out.println(new PegDownProcessor(Parser.ALL).markdownToHtml(sb.toString()));
    }
}
