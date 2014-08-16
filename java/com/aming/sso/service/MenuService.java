package com.aming.sso.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aming.sso.dao.BaseDaoImpl;
import com.aming.sso.entity.TblMenu;
import com.sendtend.util.BeanTool;
import com.sendtend.util.PageUtil;

@Service
@Transactional
public class MenuService {
	protected final transient Log log = LogFactory.getLog(MenuService.class);

	@Autowired
	private BaseDaoImpl entityDao;

	/**
	 */
	public TblMenu findMenuById(Integer menuId) {
		if(menuId == null) {
			return null;
		}
		return entityDao.findEntityById("menuId", menuId, TblMenu.class);
	}
	
	/**
	 * 
	 * @param menu
	 * @return
	 */
	public List<?> findMenuByExample(TblMenu menu) {
		return entityDao.queryByExample(menu, true, "menuName");
	}

	/**
	 * Delete an existing menut entity
	 * @param menut
	 */
	public int deleteMenu(TblMenu menu) {
		if(menu == null || menu.getMenuId() == null) {
			return 0;
		}
		String sql = "delete from tbl_role_menu where menu_id=:menuId";
		int rst = entityDao.executeSQL(sql, menu);
		sql = "delete from tbl_menu where parent_id=:menuId";
		entityDao.executeSQL(sql, menu);
		sql = "delete from tbl_menu where menu_id=:menuId";
		rst = entityDao.executeSQL(sql, menu);
		return rst;
	}


	/**
	 * Save an existing menu entity
	 * @param menut
	 */
	public TblMenu saveMenu(TblMenu menu) {
		if(menu == null) {
			return null;
		}
		TblMenu existingMenu = findMenuById(menu.getMenuId());

		if (existingMenu != null) {
			if (existingMenu != menu) {
				try {
					BeanTool.copyProperties(existingMenu, menu);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			entityDao.update(existingMenu);
			return existingMenu;
		} else {
			menu = (TblMenu)entityDao.save(menu);
			return menu;
		}
	}
	
	
	
	/**
	 * 
	 * @param menu
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	public PageUtil listMenus(TblMenu menu, Integer thePage, Integer pageSize) {
		PageUtil pu = new PageUtil();
		String sql = "select new Map(tm as TblMenu,(select menuName from TblMenu where menuId=tm.parentId) as parentMenuName) " +
				"from TblMenu tm where tm.isMenu=1 and tm.menuStatus=1 order by tm.parentId,tm.menuLevel,tm.menuSort";
		List<?> list = entityDao.createQuery(sql, 1, -1);
		if(log.isDebugEnabled()) {
			log.debug("------list:" + list);
		}
		pu.setDataSet((List<?>) list);
		int count = new Integer(entityDao.createQuery("select count(o) from TblMenu o", 1, -1).get(0).toString());
		pu.setTotalCount(count);
		return pu;
	}
	
	/**
	 * 
	 * @param menu
	 * @param thePage
	 * @param pageSize
	 * @return
	 */
	public PageUtil listTreeMenus(TblMenu menu, Integer thePage, Integer pageSize) {
		PageUtil pu = new PageUtil();
		List<?> list = entityDao.queryByExample(menu, true, "parentId", "menuLevel", "menuSort");
		pu.setDataSet(list);
		return pu;
	}
}
