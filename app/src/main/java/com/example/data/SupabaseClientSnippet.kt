package com.example.data

import kotlinx.serialization.Serializable

/**
 * User Profile Model representation for Supabase payload.
 */
@Serializable
data class UserProfile(
    val userId: String,
    val userName: String,
    val activeStatus: String = "online",
    val currentMilestoneCompleted: Boolean = false,
    val isBanned: Boolean = false
)

/**
 * SUPABASE INITIALIZATION SNIPPET
 * Uses the Supabase Kotlin SDK to configure Postgrest (Database), Realtime (Sockets), and GoTrue (Auth).
 * 
 * Note: Add the following dependencies to libs.versions.toml / build.gradle.kts to compile this directly:
 * implementation("io.github.jan-tennert.supabase:postgrest-kt:<version>")
 * implementation("io.github.jan-tennert.supabase:realtime-kt:<version>")
 * implementation("io.github.jan-tennert.supabase:gotrue-kt:<version>")
 * implementation("io.ktor:ktor-client-android:<version>")
 */
object SupabaseConfigSnippet {

    /*
    import io.github.jan.supabase.SupabaseClient
    import io.github.jan.supabase.createSupabaseClient
    import io.github.jan.supabase.gotrue.Auth
    import io.github.jan.supabase.postgrest.Postgrest
    import io.github.jan.supabase.realtime.Realtime

    val supabaseClient: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://[YOUR_PROJECT_ID].supabase.co",
        supabaseKey = "[YOUR_SUPABASE_ANON_KEY]"
    ) {
        // Initialize Postgrest for data queries
        install(Postgrest)

        // Initialize Realtime for social syncing (Silent Companion Rooms)
        install(Realtime)
        
        // Initialize GoTrue for Authentication
        install(Auth)
    }
    
    suspend fun createNewUser(enteredName: String) {
        // Automatically generate a unique user ID
        val generatedUniqueId = "USER_" + java.util.UUID.randomUUID().toString().substring(0, 8)
        
        val newUser = UserProfile(
            userId = generatedUniqueId,
            userName = enteredName
        )
        
        // Upsert the user into the Supabase 'users' table using Postgrest
        supabaseClient.postgrest["users"].insert(newUser)
    }
    */
}
