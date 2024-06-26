package com.lnbiuc.livebackend.repository

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.lnbiuc.livebackend.`do`.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserRepository: BaseMapper<User>