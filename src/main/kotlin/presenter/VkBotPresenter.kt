package presenter

import data.VkUpdate

interface VkBotPresenter {

    fun processUpdates(updates: List<VkUpdate>)

}