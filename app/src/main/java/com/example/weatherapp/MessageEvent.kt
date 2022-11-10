package com.example.weatherapp

sealed class MessageEvent {
    object ShowEmptyCity : MessageEvent()
    object ShowLoadingCity : MessageEvent()
    object ShowFindCity : MessageEvent()
    object ShowNoFindCity : MessageEvent()
    object ShowErrorFindCity : MessageEvent()
    object ShowNoData : MessageEvent()
    object ShowNoMoreTime : MessageEvent()
}