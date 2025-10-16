# HubSpot CRM 集成示例项目

这是一个基于 Spring Boot 的项目，用于与 HubSpot CRM API 进行集成，实现联系人、交易等数据的同步和管理。

## 项目结构

```
├── src/
│   ├── main/
│   │   ├── java/com/example/hubspotdemo/
│   │   │   ├── config/          # 配置类
│   │   │   ├── controller/      # REST 控制器
│   │   │   ├── exception/       # 异常处理
│   │   │   ├── model/           # 数据模型
│   │   │   ├── service/         # 服务接口
│   │   │   │   └── impl/        # 服务实现
│   │   │   └── HubSpotDemoApplication.java # 主应用类
│   │   └── resources/
│   │       └── application.yml  # 应用配置文件
│   └── test/                    # 测试代码
├── pom.xml                      # Maven 配置文件
└── README.md                    # 项目说明
```

## 功能特性

- **联系人管理**：创建、查询、更新、删除联系人
- **交易管理**：创建、查询、更新、删除交易
- **关联查询**：查询联系人关联的交易
- **分页支持**：支持大数据量的分页查询
- **搜索功能**：支持根据条件搜索联系人或交易
- **统一异常处理**：统一处理 API 调用中的异常

## 技术栈

- Java 17
- Spring Boot 3.2.0
- Spring Cloud OpenFeign
- Apache HttpClient 5
- Jackson (JSON 处理)
- Lombok (减少样板代码)

## 配置说明

在 `application.yml` 文件中配置 HubSpot API 访问信息：

```yaml
hubspot:
  api:
    base-url: https://api.hubapi.com
    api-key: your-api-key-here  # 请替换为实际的 API Key
    timeout: 30000
    connect-timeout: 5000
```

## 如何运行

1. 确保已安装 Java 17 和 Maven
2. 在 `application.yml` 中配置有效的 HubSpot API Key
3. 运行以下命令启动应用：

```bash
mvn spring-boot:run
```

应用将在 http://localhost:8080 启动

## API 接口

应用提供了以下 REST API 接口：

### 联系人接口
- `GET /api/hubspot/contacts` - 获取所有联系人
- `GET /api/hubspot/contacts/paginated` - 分页获取联系人
- `GET /api/hubspot/contacts/{id}` - 根据 ID 获取联系人
- `POST /api/hubspot/contacts` - 创建联系人
- `PUT /api/hubspot/contacts/{id}` - 更新联系人
- `DELETE /api/hubspot/contacts/{id}` - 删除联系人
- `POST /api/hubspot/contacts/search` - 搜索联系人

### 交易接口
- `GET /api/hubspot/deals` - 获取所有交易
- `GET /api/hubspot/deals/paginated` - 分页获取交易
- `GET /api/hubspot/deals/{id}` - 根据 ID 获取交易
- `POST /api/hubspot/deals` - 创建交易
- `PUT /api/hubspot/deals/{id}` - 更新交易
- `DELETE /api/hubspot/deals/{id}` - 删除交易
- `POST /api/hubspot/deals/search` - 搜索交易
- `GET /api/hubspot/contacts/{id}/deals` - 获取联系人关联的交易

## 注意事项

1. 请确保使用有效的 HubSpot API Key
2. API 调用有频率限制，请合理使用分页功能
3. 生产环境中建议添加更完善的错误处理和日志记录
4. 可以根据实际需求扩展更多的 HubSpot 对象支持，如公司、票据等

## 扩展建议

1. 添加缓存层，减少对 HubSpot API 的直接调用
2. 实现数据同步定时任务
3. 添加更详细的请求日志和监控指标
4. 实现批量操作功能
5. 添加认证和授权机制

## License

This project is licensed under the MIT License