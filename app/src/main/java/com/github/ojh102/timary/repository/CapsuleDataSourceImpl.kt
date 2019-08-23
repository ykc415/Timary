package com.github.ojh102.timary.repository

import com.github.ojh102.timary.db.room.CapsuleDao
import com.github.ojh102.timary.model.Capsule
import javax.inject.Inject

internal class CapsuleDataSourceImpl @Inject constructor(
    private val capsuleDao: CapsuleDao
) : CapsuleDataSource {

    override suspend fun gets(): List<Capsule> {
        return capsuleDao.gets()
    }

    override suspend fun get(id: Long): Capsule {
        return capsuleDao.get(id)
    }

    override suspend fun createOrUpdate(capsule: Capsule) {
        return capsuleDao.createOrUpdate(capsule)
    }
}