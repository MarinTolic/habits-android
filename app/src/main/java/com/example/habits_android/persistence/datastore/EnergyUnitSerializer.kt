package com.example.habits_android.persistence.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.application.proto.EnergyUnit
import com.example.habits_android.model.OrderOfMagnitude
import com.example.habits_android.model.listOfUnits
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

/**
 * Responsible for serializing and deserializing energy unit from their protobuf format.
 */
object EnergyUnitSerializer : Serializer<EnergyUnit> {

    private val calEnergyUnit = listOfUnits.first()
    private val energyUnitBuilder = EnergyUnit.newBuilder()

    /**
     * The default value set in case the user has no stored data.
     */
    override val defaultValue: EnergyUnit = energyUnitBuilder
        .setName(calEnergyUnit.name)
        .setJoulesConversionRatio(calEnergyUnit.joulesConversionRatio)
        .setOrderOfMagnitude(
            energyUnitBuilder.orderOfMagnitude.toBuilder()
                .setPrefix(calEnergyUnit.orderOfMagnitude.prefix)
                .setOrderOfMagnitude(calEnergyUnit.orderOfMagnitude.orderOfMagnitude)
                .build()
        ).build()

    /**
     * Reads proto objects and returns their Java counterpart.
     */
    override suspend fun readFrom(input: InputStream): EnergyUnit {
        try {
            return EnergyUnit.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto:", exception)
        }
    }

    /**
     * Writes a proto object to storage from its Java representative.
     */
    override suspend fun writeTo(
        t: EnergyUnit,
        output: OutputStream
    ) = t.writeTo(output)
}

/**
 * Data store instance used for handling energy units.
 */
val Context.energyUnitDataStore: DataStore<EnergyUnit> by dataStore(
    fileName = "energy_unit.proto",
    serializer = EnergyUnitSerializer
)

/**
 * Used for updating the energy unit value stored in data store.
 *
 * @param energyUnit A POKO representation of an energy unit which will be written to storage.
 *
 * @receiver An instance of Android's context type.
 */
suspend fun Context.updateStoredEnergyUnits(energyUnit: com.example.habits_android.model.EnergyUnit) {
    this.energyUnitDataStore.updateData {
        it.toBuilder()
            .setName(energyUnit.name)
            .setJoulesConversionRatio(energyUnit.joulesConversionRatio)
            .setOrderOfMagnitude(
                it.orderOfMagnitude.toBuilder()
                    .setPrefix(it.orderOfMagnitude.prefix)
                    .setOrderOfMagnitude(it.orderOfMagnitude.orderOfMagnitude)
                    .build()
            ).build()
    }
}

/**
 * Reads the energy unit value stored in data store.
 *
 * @receiver An instance of Android's context type.
 *
 * @return A flow emitting energy unit values stored in data store.
 */
fun Context.readStoredEnergyUnit() =
    this.energyUnitDataStore.data
        .map {
            com.example.habits_android.model.EnergyUnit(
                name = it.name,
                joulesConversionRatio = it.joulesConversionRatio,
                orderOfMagnitude = OrderOfMagnitude(
                    prefix = it.orderOfMagnitude.prefix,
                    orderOfMagnitude = it.orderOfMagnitude.orderOfMagnitude
                )
            )
        }