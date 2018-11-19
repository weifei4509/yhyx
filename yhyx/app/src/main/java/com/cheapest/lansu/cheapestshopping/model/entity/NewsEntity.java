package com.cheapest.lansu.cheapestshopping.model.entity;

import java.util.List;

/**
 * @author 码农哥
 * @date 2018/7/14 0014  22:40
 * @email 441293364@qq.com
 * @TODO <p/>
 * ** *** ━━━━━━神兽出没━━━━━━
 * ** ***       ┏┓　　  ┏┓
 * ** *** 	   ┏┛┻━━━┛┻┓
 * ** *** 　  ┃　　　　　　　┃
 * ** *** 　　┃　　　━　　　┃
 * ** *** 　　┃　┳┛　┗┳　┃
 * ** *** 　　┃　　　　　　　┃
 * ** *** 　　┃　　　┻　　　┃
 * ** *** 　　┃　　　　　　　┃
 * ** *** 　　┗━┓　　　┏━┛
 * ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 * ** *** 　　　　┃　　　┃
 * ** *** 　　　　┃　　　┗━━━┓
 * ** *** 　　　　┃　　　　　　　┣┓
 * ** *** 　　　　┃　　　　　　　┏┛
 * ** *** 　　　　┗┓┓┏━┳┓┏┛
 * ** *** 　　　　  ┃┫┫  ┃┫┫
 * ** *** 　　　　  ┗┻┛　┗┻┛
 */
public class NewsEntity {

	private int total;
	private int size;
	private int page;
	private int pages;
	private int curSize;
	private List<NewsEntity.DatasBean> datas;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getCurSize() {
		return curSize;
	}

	public void setCurSize(int curSize) {
		this.curSize = curSize;
	}

	public List<NewsEntity.DatasBean> getDatas() {
		return datas;
	}

	public void setDatas(List<NewsEntity.DatasBean> datas) {
		this.datas = datas;
	}


	public static class DatasBean {

		private int id;
		private String title;//": "静夜思",
		private String author;//": "李白",
		private String summary;//": "李白的诗",//简介
		private String createTime;//": "",//创建时间
		private String content;//富文本内容
		private int praiseNum;//": 1,//点赞数
		private int viewNum;//": 2,//浏览数
		private String iconUrl;//": ""//图标地址
		private boolean praised;//当前用户是否已点赞

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public boolean isPraised() {
			return praised;
		}

		public void setPraised(boolean praised) {
			this.praised = praised;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public int getPraiseNum() {
			return praiseNum;
		}

		public void setPraiseNum(int praiseNum) {
			this.praiseNum = praiseNum;
		}

		public int getViewNum() {
			return viewNum;
		}

		public void setViewNum(int viewNum) {
			this.viewNum = viewNum;
		}

		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}
	}


}
