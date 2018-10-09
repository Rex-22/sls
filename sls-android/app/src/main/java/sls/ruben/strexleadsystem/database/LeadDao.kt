package sls.ruben.strexleadsystem.database

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.model.LeadModel
import sls.ruben.strexleadsystem.model.StaffModel

@Dao
interface LeadDao {

    @Query("SELECT * FROM lead WHERE id = :id")
    fun get(id: Int): Flowable<LeadModel>


    @Query("SELECT * FROM lead")
    fun getAll(): Flowable<List<LeadModel>>

}