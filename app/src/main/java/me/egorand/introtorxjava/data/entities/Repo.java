package me.egorand.introtorxjava.data.entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import me.egorand.introtorxjava.data.db.ReposDatabase;

/**
 * @author Egor
 */
@Table(database = ReposDatabase.class)
public class Repo extends BaseModel {

    @PrimaryKey long id;
    @Column String name;
    @Column String description;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
