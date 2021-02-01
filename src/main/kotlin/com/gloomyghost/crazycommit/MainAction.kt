package com.gloomyghost.crazycommit

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.vcs.CommitMessageI
import com.intellij.openapi.vcs.VcsDataKeys
import com.intellij.openapi.vcs.ui.Refreshable

class MainAction : AnAction(), DumbAware {
    override fun actionPerformed(e: AnActionEvent) {
        val checkinPanel = getCheckinPanel(e) ?: return
        val commitMessage = loadCommitMessage()
        if (commitMessage.isNotEmpty()) {
            checkinPanel.setCommitMessage(commitMessage)
        }
    }

    private fun loadCommitMessage(): String {
        val list = CommitMessage().commitMessage
        val commitMessageString = list.shuffled().take(1).toString()
        return commitMessageString.substring(1, commitMessageString.lastIndexOf("]"))
    }

    private fun getCheckinPanel(e: AnActionEvent?): CommitMessageI? {
        if (e == null) {
            return null
        }
        val data = Refreshable.PANEL_KEY.getData(e.dataContext)
        return if (data is CommitMessageI) {
            data
        } else VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(e.dataContext)
    }
}