package sls.ruben.strexleadsystem.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import sls.ruben.strexleadsystem.util.Error


@Entity(tableName = "Lead")
data class LeadModel(
        @PrimaryKey
        @SerializedName("id") var id: Int? = 0,
        @SerializedName("first_name") var firstname: String? = "",
        @SerializedName("last_name") var lastname: String? = "",
        @SerializedName("tell") var tell: String? = "",
        @SerializedName("cell") var cell: String? = "",
        @SerializedName("email") var email: String? = "",
        @SerializedName("staff_id") var staffId: String? = "",
        @SerializedName("company_id") var companyId: String? = "",
        @Ignore @SerializedName("company") var company: CompanyModel = CompanyModel(),

        /* Meta data */
        @Ignore var errorCode: Error = Error.NONE
)