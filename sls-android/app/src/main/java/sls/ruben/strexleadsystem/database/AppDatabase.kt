package sls.ruben.strexleadsystem.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.model.StaffModel

@Database(entities = [(StaffModel::class), (LeadModel::class), (CompanyModel::class)], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun staffDao() : StaffDao
    abstract fun leadDao(): LeadDao
    abstract fun companyDao(): CompanyDao

}