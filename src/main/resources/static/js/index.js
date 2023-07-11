/**
 * 请求封装
 */
import axios from 'axios';

const service = axios.create({
    // 因为项目前端和后台是放在一起的，所以这里直接用 VUE_APP_CONTEXT_PATH 即可，如果分开部署用 VUE_APP_BASE_API
    withCredentials: true, // 是否携带cookie
    timeout: 5000 // 请求超时时间
})

//添加请求拦截器   请求接口统一添加token
service.interceptors.request.use(function(config){
    //比如是否需要设置 token
    config.headers.token=localStorage.getItem('token')
    return config
})
