package com.lnbiuc.livebackend.repository

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.lnbiuc.livebackend.`do`.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: BaseMapper<User> {}