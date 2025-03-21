package com.sdc.aicookmate.model

enum class MyPageSection(val title: String, val route: String, val subtitle: String? = null) {
    NOTICE("공지사항", "notice"),
    EVENT("이벤트", "event", "공지사항"), // 이벤트에 공지사항 추가
    FAQ("자주 묻는 질문", "faq"),
    CONTACT("1:1 문의하기", "contact")
}