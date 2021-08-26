package com.example.githubbrowser.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class GithubBrowserEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val repoName:String,
    val repoDes:String,
    val repoUrl:String,
    val ownerName:String,
    val issueCount:Int
) : Parcelable {

//    private constructor(parcel: Parcel): this(
//        id = parcel.readInt(),
//        repoName = parcel.readString() ?: "",
//        repoDes = parcel.readString() ?: "",
//        repoUrl = parcel.readString() ?: "",
//        ownerName = parcel.readString() ?: "",
//        issueCount = parcel.readInt()
//    )
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(dest: Parcel?, flags: Int) {
//        dest?.run {
//            writeInt(id)
//            writeString(repoName)
//            writeString(repoDes)
//            writeString(repoUrl)
//            writeString(ownerName)
//            writeInt(issueCount)
//        }
//    }
//
//    companion object CREATOR : Parcelable.Creator<GithubBrowserEntity> {
//        override fun createFromParcel(parcel: Parcel): GithubBrowserEntity {
//            return GithubBrowserEntity(parcel)
//        }
//
//        override fun newArray(size: Int): Array<GithubBrowserEntity?> {
//            return arrayOfNulls(size)
//        }
//    }

}