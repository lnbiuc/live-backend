package com.lnbiuc.livebackend.repository

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.lnbiuc.livebackend.`do`.PushAddress
import org.apache.ibatis.annotations.Mapper

@Mapper
interface PushAddressRepository : BaseMapper<PushAddress>