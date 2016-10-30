package com.codepath.apps.tripletweet.database;

import com.codepath.apps.tripletweet.models.Tweet;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)

public class MyDatabase {

    public static final String NAME = "RestClientDatabase";

    public static final int VERSION = 1;

}


