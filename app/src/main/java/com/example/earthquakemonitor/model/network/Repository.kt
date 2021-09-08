package com.example.earthquakemonitor.model.network

import androidx.lifecycle.LiveData
import com.example.earthquakemonitor.model.data.Earthquake
import com.example.earthquakemonitor.model.data.EqDataBase
import com.example.earthquakemonitor.model.network.EqJsonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val dataBase: EqDataBase) {

//  ya no actualiza desde la baser de datos
//  se pierde la funcionalidad del livedata val eqList : LiveData<MutableList<Earthquake>> = dataBase.eqDao.getEarthquakes()
    //Retornaba un MutableList

    //Volvemos a poner un parametro en el método

    suspend fun  fetchEarthquakes(sortByMagnitude: Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
/*            val eqList: MutableList<Earthquake> = mutableListOf<Earthquake>()
              eqList.add(Earthquake("1","Colombia",4.1,13231321L,28.575,-102.5654))
              eqList.add(Earthquake("2","México",3.3,13231321L,28.575,-102.5654))
              eqList.add(Earthquake("3","Perú",5.3,13231321L,28.575,-102.5654))
              eqList.add(Earthquake("4","Argentina",2.3,13231321L,28.575,-102.5654))
              eqList.add(Earthquake("5","Colombia",4.3,13231321L,28.575,-102.5654))
              eqList*/
            val eqJsonResponse: EqJsonResponse = service.getLastHourEarthquakes()
            val eqList = parseEqResult(eqJsonResponse)
            dataBase.eqDao.insertAll(eqList)
            fetchEarthquakesDatabase(sortByMagnitude)
        }

    }

    suspend fun  fetchEarthquakesDatabase(sortByMagnitude: Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
            if (sortByMagnitude) {
                dataBase.eqDao.getEarthquakesMagnitude()
            }
            else {
                dataBase.eqDao.getEarthquakes()
            }
        }
    }


/*    private fun parseEqResult(eqListString: String): MutableList<Earthquake>{
        val eqJSONObject = JSONObject(eqListString)
        val featuresJsonArray = eqJSONObject.getJSONArray("features")
        val eqListJson = mutableListOf<Earthquake>()

        for (i in 0 until featuresJsonArray.length()){
            val featuresJsonObject = featuresJsonArray[i] as JSONObject
            val id = featuresJsonObject.getString("id")

            val propertiesJSONObject = featuresJsonObject.getJSONObject("properties")
            val magnitude = propertiesJSONObject.getDouble("mag")
            val place = propertiesJSONObject.getString("place")
            val time = propertiesJSONObject.getLong("time")

            val geometryJSONObject = featuresJsonObject.getJSONObject("geometry")
            val coordinateJSONArray = geometryJSONObject.getJSONArray("coordinates")
            val longitude = coordinateJSONArray.getDouble(0)
            val latitude = coordinateJSONArray.getDouble(1)

            val earthquake = Earthquake(id,place,magnitude,time,longitude,latitude)
            eqListJson.add(earthquake)

        }

        return eqListJson
    }*/

    private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake>{

        val eqListJson = mutableListOf<Earthquake>()
        val featureList = eqJsonResponse.features
        for(feature in featureList){
            val properties = feature.properties

            val id = feature.id

            val magnitude =  properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry
            val latitude = geometry.latitude
            val longitude = geometry.longitude

            eqListJson.add(Earthquake(id,place,magnitude,time,latitude,longitude))

        }
        return eqListJson
    }
}