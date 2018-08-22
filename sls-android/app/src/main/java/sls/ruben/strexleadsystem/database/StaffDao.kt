package sls.ruben.strexleadsystem.database

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import sls.ruben.strexleadsystem.model.StaffModel

@Dao
interface StaffDao {

    @Query("SELECT * FROM staff WHERE id = :id")
    fun get(id: Int): Flowable<StaffModel>

}