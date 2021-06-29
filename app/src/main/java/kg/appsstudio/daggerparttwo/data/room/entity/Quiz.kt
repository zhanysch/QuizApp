package kg.appsstudio.daggerparttwo.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = "quiz")
@Parcelize
data class Quiz(

    @ColumnInfo(name = "answers")
    @SerializedName("answers")
    var answer : @RawValue List<String>,

    @ColumnInfo(name = "correctIndex")
    @SerializedName("correctIndex")
    var correctIndex: Int,

    @ColumnInfo(name = "question")
    @SerializedName("question")
    var question : String

): Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    constructor():this( answer = listOf(), correctIndex = 0 , question = "")
}
