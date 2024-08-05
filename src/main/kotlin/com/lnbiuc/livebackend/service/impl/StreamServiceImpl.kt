package com.lnbiuc.livebackend.service.impl


import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.baomidou.mybatisplus.core.toolkit.StringUtils
import com.lnbiuc.livebackend.controller.GlobalExceptionHandler
import com.lnbiuc.livebackend.`do`.PushAddress
import com.lnbiuc.livebackend.repository.PushAddressRepository
import com.lnbiuc.livebackend.service.StreamService
import com.lnbiuc.livebackend.utils.UserUtils
import com.lnbiuc.livebackend.utils.stream.StreamUtils
import com.tencentcloudapi.common.AbstractModel
import com.tencentcloudapi.common.Credential
import com.tencentcloudapi.common.exception.TencentCloudSDKException
import com.tencentcloudapi.common.profile.ClientProfile
import com.tencentcloudapi.common.profile.HttpProfile
import com.tencentcloudapi.live.v20180801.LiveClient
import com.tencentcloudapi.live.v20180801.models.DescribeLivePushAuthKeyRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


@Service
class StreamServiceImpl(private val pushAddressRepository: PushAddressRepository) : StreamService {

    @Value("\${tencent.SecretId}")
    lateinit var SECRET_ID: String

    @Value("\${tencent.SecretKey}")
    lateinit var SECRET_KEY: String

    @Value("\${tencent.PushDomainName}")
    lateinit var PUSH_DOMAIN_NAME: String

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    override fun createPushAddress(): PushAddress {
        var authKey = ""
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            val cred = Credential(SECRET_ID, SECRET_KEY)
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            val httpProfile = HttpProfile()
            httpProfile.endpoint = "live.tencentcloudapi.com"
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            val clientProfile = ClientProfile()
            clientProfile.httpProfile = httpProfile
            // 实例化要请求产品的client对象,clientProfile是可选的
            val client = LiveClient(cred, "", clientProfile)
            // 实例化一个请求对象,每个接口都会对应一个request对象
            val req = DescribeLivePushAuthKeyRequest()
            req.domainName = PUSH_DOMAIN_NAME
            // 返回的resp是一个DescribeLivePushAuthKeyResponse的实例，与请求对象对应
            val resp = client.DescribeLivePushAuthKey(req)
            // 输出json格式的字符串回包
            val jsonString = AbstractModel.toJsonString(resp)
            val jsonObj = JSON.parse(jsonString) as JSONObject
            authKey = jsonObj.getJSONObject("Response").getJSONObject("PushAuthKeyInfo").getString("MasterAuthKey")
        } catch (e: TencentCloudSDKException) {
            e.printStackTrace()
        }

        if (StringUtils.isEmpty(authKey)) {
            throw Exception("request Tencent AuthKey fail")
        }
        val user = UserUtils.getCurrentUser()


        val streamName = generateRandomName(6)
        val pushUrl = StreamUtils.getSafeUrl(authKey, streamName, oneYear())

        val pushAddress = PushAddress(null, streamName, PUSH_DOMAIN_NAME, pushUrl, user.id, LocalDateTime.now())

        pushAddressRepository.insert(pushAddress)

        return pushAddress
    }

    fun generateRandomName(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun oneYear(): Long {
        val oneYearInSeconds = 365 * 24 * 60 * 60 // 一年的秒数
        val oneYearDuration = Instant.now().plus(oneYearInSeconds.toLong(), ChronoUnit.SECONDS)
        return oneYearDuration.toEpochMilli()
    }
}