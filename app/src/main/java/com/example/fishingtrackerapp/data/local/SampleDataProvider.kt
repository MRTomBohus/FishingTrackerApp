package com.example.fishingtrackerapp.data.local

import com.example.fishingtrackerapp.domain.model.Catch
import com.example.fishingtrackerapp.domain.model.FishingSpot

/**
 * Provides local sample fishing spots and fallback catch data for the application.
 */
object SampleDataProvider {

    val catches = listOf(
        Catch(
            id = 1,
            fishType = "Kapor",
            weight = 5.2f,
            length = 65f,
            location = "VN Hričov [K]",
            bait = "Boilies"
        ),
        Catch(
            id = 2,
            fishType = "Šťuka",
            weight = 3.8f,
            length = 82f,
            location = "VN Divinka [K]",
            bait = "Wobler"
        ),
        Catch(
            id = 3,
            fishType = "Pstruh potočný",
            weight = 0.45f,
            length = 32f,
            location = "Rajčianka č. 2 a [P]",
            bait = "Mucha"
        ),
        Catch(
            id = 4,
            fishType = "Kapor",
            weight = 8.1f,
            length = 78f,
            location = "VN Hričov [K]",
            bait = "Kukurica"
        )
    )

    val fishingSpots = listOf(
        FishingSpot(
            id = 1,
            name = "Biely potok (Žilina)",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový potok v pôsobnosti MsO SRZ Žilina.",
            latitude = 49.2270,
            longitude = 18.7440
        ),
        FishingSpot(
            id = 2,
            name = "Bitarovský potok",
            type = "Lososové pstruhové",
            description = "Lovný pstruhový potok v oblasti Bitarovej.",
            latitude = 49.1770,
            longitude = 18.6680
        ),
        FishingSpot(
            id = 3,
            name = "Bradová",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový revír v okolí Žiliny.",
            latitude = 49.2100,
            longitude = 18.8000
        ),
        FishingSpot(
            id = 4,
            name = "Bystrička (Žilina)",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový revír v okolí Bystričky.",
            latitude = 49.2060,
            longitude = 18.7600
        ),
        FishingSpot(
            id = 5,
            name = "Divina",
            type = "Lososové pstruhové",
            description = "Lovný pstruhový revír v oblasti Diviny.",
            latitude = 49.2760,
            longitude = 18.6950
        ),
        FishingSpot(
            id = 6,
            name = "Hradský potok",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový potok v pôsobnosti MsO SRZ Žilina.",
            latitude = 49.1450,
            longitude = 18.6550
        ),
        FishingSpot(
            id = 7,
            name = "Javorina",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový revír v horskej oblasti.",
            latitude = 49.1900,
            longitude = 18.9200
        ),
        FishingSpot(
            id = 8,
            name = "Kuneradský potok",
            type = "Lososové pstruhové",
            description = "Lovný pstruhový potok v oblasti Kuneradu.",
            latitude = 49.0930,
            longitude = 18.7250
        ),
        FishingSpot(
            id = 9,
            name = "Kysuca č. 1",
            type = "Kaprové s výskytom hlavátky",
            description = "Lovný riečny revír na rieke Kysuca.",
            latitude = 49.2870,
            longitude = 18.7760
        ),
        FishingSpot(
            id = 10,
            name = "Lipovec",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový revír v okolí Žiliny.",
            latitude = 49.1600,
            longitude = 18.8100
        ),
        FishingSpot(
            id = 11,
            name = "Lom Nezbud",
            type = "Kaprové",
            description = "Lovný kaprový revír v oblasti Nezbudskej Lúčky.",
            latitude = 49.1760,
            longitude = 18.9070
        ),
        FishingSpot(
            id = 12,
            name = "Materiálová jama Celulózka",
            type = "Kaprové",
            description = "Lovný stojatý revír v oblasti Žiliny.",
            latitude = 49.2190,
            longitude = 18.7550
        ),
        FishingSpot(
            id = 13,
            name = "Porubský potok – chovné potoky",
            type = "Lososové pstruhové",
            description = "Chovné pstruhové potoky v oblasti Lietavskej Lúčky a Porúbky.",
            latitude = 49.1520,
            longitude = 18.7050
        ),
        FishingSpot(
            id = 14,
            name = "Porubský potok (Žilina)",
            type = "Lososové pstruhové",
            description = "Lovný pstruhový potok v oblasti Porúbky.",
            latitude = 49.1550,
            longitude = 18.7000
        ),
        FishingSpot(
            id = 15,
            name = "Rajčanka č. 1",
            type = "Lososové lipňové",
            description = "Lovný lipňový revír na rieke Rajčanka.",
            latitude = 49.1650,
            longitude = 18.7050
        ),
        FishingSpot(
            id = 16,
            name = "Rajčanka – chovné potoky",
            type = "Lososové pstruhové",
            description = "Chovné pstruhové prítoky Rajčanky.",
            latitude = 49.1200,
            longitude = 18.7000
        ),
        FishingSpot(
            id = 17,
            name = "Rosinka",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový revír v okolí Rosiny.",
            latitude = 49.1900,
            longitude = 18.7680
        ),
        FishingSpot(
            id = 18,
            name = "Stráňavský potok",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový potok v oblasti Stráňav.",
            latitude = 49.1680,
            longitude = 18.8200
        ),
        FishingSpot(
            id = 19,
            name = "Štrkovisko Brodno",
            type = "Kaprové",
            description = "Lovný kaprový revír v časti Brodno.",
            latitude = 49.2540,
            longitude = 18.7570
        ),
        FishingSpot(
            id = 20,
            name = "Struháreň",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový revír v pôsobnosti MsO SRZ Žilina.",
            latitude = 49.1800,
            longitude = 18.8500
        ),
        FishingSpot(
            id = 21,
            name = "Teplička (Žilina)",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový revír v oblasti Tepličky nad Váhom.",
            latitude = 49.2290,
            longitude = 18.8050
        ),
        FishingSpot(
            id = 22,
            name = "Váh č. 13",
            type = "Kaprové",
            description = "Lovný kaprový riečny revír na rieke Váh.",
            latitude = 49.1950,
            longitude = 18.6600
        ),
        FishingSpot(
            id = 23,
            name = "Váh č. 14",
            type = "Kaprové",
            description = "Lovný kaprový riečny revír na rieke Váh v okolí Žiliny.",
            latitude = 49.2240,
            longitude = 18.7390
        ),
        FishingSpot(
            id = 24,
            name = "Varínka",
            type = "Lososové pstruhové",
            description = "Lovný pstruhový revír na rieke Varínka.",
            latitude = 49.2005,
            longitude = 18.8765
        ),
        FishingSpot(
            id = 25,
            name = "Varínka – chovné potoky",
            type = "Lososové pstruhové",
            description = "Chovné pstruhové prítoky Varínky.",
            latitude = 49.1900,
            longitude = 18.9300
        ),
        FishingSpot(
            id = 26,
            name = "VN Divinka",
            type = "Kaprové",
            description = "Lovný kaprový revír na vodnej nádrži Divinka.",
            latitude = 49.2630,
            longitude = 18.7030
        ),
        FishingSpot(
            id = 27,
            name = "VN Hričov",
            type = "Kaprové",
            description = "Lovný kaprový revír na vodnej nádrži Hričov.",
            latitude = 49.2375,
            longitude = 18.6453
        ),
        FishingSpot(
            id = 28,
            name = "VN Košiare",
            type = "Kaprové",
            description = "Lovný kaprový revír pri Rajci.",
            latitude = 49.0800,
            longitude = 18.6400
        ),
        FishingSpot(
            id = 29,
            name = "VN Strážov",
            type = "Kaprové",
            description = "Lovný kaprový revír v oblasti Strážova.",
            latitude = 49.2260,
            longitude = 18.7200
        ),
        FishingSpot(
            id = 30,
            name = "Zlatný potok",
            type = "Lososové pstruhové",
            description = "Chovný pstruhový potok v pôsobnosti MsO SRZ Žilina.",
            latitude = 49.1400,
            longitude = 18.8800
        )
    )

    fun getCatchById(id: Int): Catch? {
        return catches.firstOrNull { it.id == id }
    }
}