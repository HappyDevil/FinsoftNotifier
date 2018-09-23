package com.example.daniil.finsoftnotifier.entities

import android.os.Parcel
import android.os.Parcelable
import com.example.daniil.finsoftnotifier.constants.DEFAULT_YEAR
import com.example.daniil.finsoftnotifier.helpers.getMonthName

data class YearMonth(val year: String,
                     val month: Int) : Comparable<YearMonth>, Parcelable {

    companion object {
        @JvmField
        @Suppress("unused")
        val CREATOR: Parcelable.Creator<YearMonth> = createParcel { YearMonth(it) }
    }

    constructor(parcel: Parcel) : this(parcel.readString() ?: DEFAULT_YEAR, parcel.readInt())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(year)
        dest.writeInt(month)
    }

    override fun describeContents(): Int {
        return 0;
    }

    val name: String = getMonthName(month)

    override fun compareTo(other: YearMonth): Int {
        val yearValue = this.year.toInt()
        val otherYearValue = other.year.toInt()
        return when {
            yearValue > otherYearValue -> 1
            yearValue < otherYearValue -> -1
            this.month > other.month -> 1
            this.month < other.month -> -1
            else -> 0
        }
    }
}

inline fun <reified T : Parcelable> createParcel(
        crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
        object : Parcelable.Creator<T> {
            override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
        }