/*
 * Copyright 2021 SOUP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anggit97.detail

import androidx.core.view.isVisible
import com.anggit97.core.ext.loadAsync
import com.anggit97.detail.databinding.ActivityDetailBinding
import com.anggit97.detail.databinding.DetailHeaderBinding
import com.anggit97.detail.databinding.DetailShareBinding

interface DetailViewRenderer {

    fun ActivityDetailBinding.render(uiModel: HeaderUiModel) {
        header.render(uiModel)
        share.render(uiModel)
    }

    private fun DetailHeaderBinding.render(uiModel: HeaderUiModel) {
        titleView.text = uiModel.movie.title

        val item = uiModel.movie
//        ageBgView.root.setBackgroundResource(item.getAgeBackground())
//        ageView.root.asyncText(item.getSimpleAgeLabel())
//        newView.root.isVisible = item.isNew()
//        bestView.root.isVisible = item.isBest()
//        dDayView.root.isVisible = item.isDDay()
//        dDayView.root.asyncText(item.getDDayLabel())

//        openDateText.text = uiModel.movie.openDate
//        val openDateVisible = uiModel.movie.openDate.isNotEmpty()
//        openDateLabel.isVisible = openDateVisible
//        openDateText.isVisible = openDateVisible

//        ageText.text = uiModel.movie.getAgeLabel()

        val genres = uiModel.movie.genres
        if (genres != null) {
            genreText.text = genres.joinToString(separator = ", ")
        }
        val genresVisible = genres != null
        genreLabel.isVisible = genresVisible
        genreText.isVisible = genresVisible

        val nations = uiModel.nations
        nationText.text = nations.joinToString(separator = ", ")
        val nationsVisible = nations.isNotEmpty()
        nationLabel.isVisible = nationsVisible
        nationText.isVisible = nationsVisible

        val showTm = uiModel.showTm
        val showTmVisible = showTm > 0
        if (showTm > 0) {
            runningTimeText.apply {
                text = context.getString(R.string.time_minute, uiModel.showTm)
            }
        }
        runningTimeLabel.isVisible = showTmVisible
        runningTimeText.isVisible = showTmVisible

//        val companies = uiModel.companies
//            .asSequence()
//            .filter { it.companyPartNm.contains("배급") }
//            .map { it.companyNm }
//            .joinToString(separator = ", ")
//        val companiesVisible = companies.isNotBlank()
//        if (companiesVisible) {
//            companyText.text = companies
//        }
//        companyLabel.isVisible = companiesVisible
//        companyText.isVisible = companiesVisible
    }

    private fun DetailShareBinding.render(uiModel: HeaderUiModel) {
        titleView.text = uiModel.movie.title
        posterCard.loadAsync(uiModel.movie.getPosterUrl())

//        val item = uiModel.movie
//        ageBgView.root.setBackgroundResource(item.getAgeBackground())
//        ageView.root.asyncText(item.getSimpleAgeLabel())
//        newView.root.isVisible = item.isNew()
//        bestView.root.isVisible = item.isBest()
//        dDayView.root.isVisible = item.isDDay()
//        dDayView.root.asyncText(item.getDDayLabel())
//
//        openDateText.text = uiModel.movie.openDate
//        val openDateVisible = uiModel.movie.openDate.isNotEmpty()
//        openDateLabel.isVisible = openDateVisible
//        openDateText.isVisible = openDateVisible
//
//        ageText.text = uiModel.movie.getAgeLabel()
//
//        val genres = uiModel.movie.genres
//        if (genres != null) {
//            genreText.text = genres.joinToString(separator = ", ")
//        }
//        val genresVisible = genres != null
//        genreLabel.isVisible = genresVisible
//        genreText.isVisible = genresVisible
//
//        val nations = uiModel.nations
//        nationText.text = nations.joinToString(separator = ", ")
//        val nationsVisible = nations.isNotEmpty()
//        nationLabel.isVisible = nationsVisible
//        nationText.isVisible = nationsVisible
//
//        val showTm = uiModel.showTm
//        val showTmVisible = showTm > 0
//        if (showTm > 0) {
//            runningTimeText.apply {
//                text = context.getString(R.string.time_minute, uiModel.showTm)
//            }
//        }
//        runningTimeLabel.isVisible = showTmVisible
//        runningTimeText.isVisible = showTmVisible
//
//        val companies = uiModel.companies
//            .asSequence()
//            .filter { it.companyPartNm.contains("배급") }
//            .map { it.companyNm }
//            .joinToString(separator = ", ")
//        val companiesVisible = companies.isNotBlank()
//        if (companiesVisible) {
//            companyText.text = companies
//        }
//        companyLabel.isVisible = companiesVisible
//        companyText.isVisible = companiesVisible
    }
}
