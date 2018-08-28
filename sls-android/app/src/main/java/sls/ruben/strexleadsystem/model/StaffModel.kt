package sls.ruben.strexleadsystem.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import sls.ruben.strexleadsystem.util.Error

@Entity(tableName = "Staff")
data class StaffModel(
        @PrimaryKey
        @SerializedName("id") var id: Int? = 0,
        @SerializedName("username") var username: String? = "",
        @SerializedName("email") var email: String? = "",
        @Ignore @SerializedName("password") var password: String? = "", // Don't want to store the password...
        @SerializedName("remember_token") var authToken: String? = "",

        /* Meta data */
        @Ignore var errorCode: Error = Error.NONE
)