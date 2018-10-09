package sls.ruben.strexleadsystem.database

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import sls.ruben.strexleadsystem.model.CompanyModel
import sls.ruben.strexleadsystem.model.StaffModel

@Dao
interface CompanyDao {

    @Query("SELECT * FROM company WHERE id = :id")
    fun get(id: Int): Flowable<CompanyModel>

    @Query("SELECT * FROM company")
    fun getAll(): Flowable<List<CompanyModel>>

}