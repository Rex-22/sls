package sls.ruben.strexleadsystem.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sls.ruben.strexleadsystem.model.StaffModel

@Database(entities = [(StaffModel::class)], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun staffDao() : StaffDao

}