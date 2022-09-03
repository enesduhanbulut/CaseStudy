package com.duhan.feature_name.domain

import com.duhan.feature_name.data.repository.NameRepository

class GetNames(private val nameRepository: NameRepository) {
    operator fun invoke() = nameRepository.getAllNames()

}