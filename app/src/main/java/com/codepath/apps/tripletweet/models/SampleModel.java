package com.codepath.apps.tripletweet.models;

import com.codepath.apps.tripletweet.database.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.List;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the DBFlow wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
@Table(database = MyDatabase.class)
@Parcel(analyze={SampleModel.class})
public class SampleModel extends BaseModel {

	public void SampleModel(){}

	@PrimaryKey
	@Column
	Long id;

	// Define table fields
	@Column
	public String name;

	public SampleModel() {
		super();
	}

/*	@Column
	@ForeignKey(saveForeignKeyModel = false)
	Tweet tweet;

	public void setOrganization(Tweet tweet) {
		this.tweet = tweet;
	}*/

	// Parse model from JSON
	public SampleModel(JSONObject object){
		super();

		try {
			this.name = object.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// Getters
	public String getName() {
		return name;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	// Record Finders
	public static SampleModel byId(long id) {
		return new Select().from(SampleModel.class).where(SampleModel_Table.id.eq(id)).querySingle();
	}

	public static List<SampleModel> recentItems() {
		return new Select().from(SampleModel.class).orderBy(SampleModel_Table.id, false).limit(300).queryList();
	}
}
