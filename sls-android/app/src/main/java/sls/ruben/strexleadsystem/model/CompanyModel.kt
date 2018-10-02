package sls.ruben.strexleadsystem.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import sls.ruben.strexleadsystem.util.Error


@Entity(tableName = "Lead")
data class CompanyModel(
        @PrimaryKey
        @SerializedName("id") var id: Int? = 0,
        @SerializedName("name") var name: String? = "",
        @SerializedName("address") var address: String? = "",
        @SerializedName("tell") var tell: String? = "",
        @SerializedName("website") var website: String? = "",

        /* Meta data */
        @Ignore var errorCode: Error = Error.NONE
)