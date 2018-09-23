package com.example.daniil.finsoftnotifier.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.daniil.finsoftnotifier.models.StatsModel

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface StatsView : MvpView {
    fun injectData(statsModel: StatsModel)
    fun startPosition()
    fun endPosition()
    fun simplePosition()
    fun oneElementPosition()
}