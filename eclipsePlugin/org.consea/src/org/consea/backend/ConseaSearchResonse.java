package org.consea.backend;

public class ConseaSearchResonse {

	private String attvalue;
	private String author;
	private String changedby;
	private String changedon;
	private String clsname;
	private String cmpname;
	private String createdon;
	private String descript;
	private String type;

	public ConseaSearchResonse(String attvalue, String author,
			String changedby, String changedon, String clsname, String cmpname,
			String createdon, String descript, String type) {

		this.attvalue = attvalue;
		this.author = author;
		this.changedby = changedby;
		this.changedon = changedon;
		this.clsname = clsname;
		this.cmpname = cmpname;
		this.createdon = createdon;
		this.descript = descript;
		this.type = type;
	}

	@Override
	public String toString() {
		return "ConseaSearchResonse [attvalue=" + attvalue + ", author="
				+ author + ", changedby=" + changedby + ", changedon="
				+ changedon + ", clsname=" + clsname + ", cmpname=" + cmpname
				+ ", createdon=" + createdon + ", descript=" + descript
				+ ", type=" + type + "]";
	}

	public String getAttvalue() {
		return attvalue;
	}

	public String getAuthor() {
		return author;
	}

	public String getChangedby() {
		return changedby;
	}

	public String getChangedon() {
		return changedon;
	}

	public String getClsname() {
		return clsname;
	}

	public String getCmpname() {
		return cmpname;
	}

	public String getCreatedon() {
		return createdon;
	}

	public String getDescript() {
		return descript;
	}

	public String getType() {
		return type;
	}

}
