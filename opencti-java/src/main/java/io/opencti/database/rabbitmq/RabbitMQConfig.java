package io.opencti.database.rabbitmq;

import io.opencti.common.config.RabbitMQProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.List;

/**
 * RabbitMQ Spring 配置
 * 重写源文件: opencti-platform/opencti-graphql/src/database/rabbitmq.js 第 41-48 行, 110-156 行
 * 
 * 源码:
 * const amqpUri = () => {
 *   const ssl = USE_SSL ? 's' : '';
 *   return `amqp${ssl}://${HOSTNAME}:${PORT}${VHOST_PATH}`;
 * };
 * 
 * const amqpCred = () => {
 *   return { credentials: amqp.credentials.plain(USERNAME, PASSWORD) };
 * };
 * 
 * const amqpExecute = async (execute) => {
 *   const connOptions = USE_SSL ? {
 *     ...amqpCred(),
 *     ...configureCA(RABBITMQ_CA),
 *     cert: RABBITMQ_CA_CERT,
 *     key: RABBITMQ_CA_KEY,
 *     pfx: RABBITMQ_CA_PFX,
 *     passphrase: RABBITMQ_CA_PASSPHRASE,
 *     rejectUnauthorized: RABBITMQ_REJECT_UNAUTHORIZED,
 *   } : amqpCred();
 *   ...
 * };
 */
@Configuration
public class RabbitMQConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);

    /**
     * 创建 RabbitMQ 连接工厂
     * 重写源文件: rabbitmq.js 第 41-48 行, 110-119 行
     */
    @Bean
    @ConditionalOnMissingBean
    public ConnectionFactory rabbitConnectionFactory(RabbitMQProperties properties) {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        
        factory.setHost(properties.hostname());
        factory.setPort(properties.port());
        factory.setUsername(properties.username());
        factory.setPassword(properties.password());
        
        String vhost = properties.vhost();
        factory.setVirtualHost(vhost != null && !vhost.isEmpty() ? vhost : "/");
        
        factory.setConnectionTimeout(properties.connectionTimeout());
        
        if (properties.useSsl()) {
            configureSsl(factory, properties);
        }
        
        log.info("[RABBITMQ] Connection factory configured for {}:{}", 
                properties.hostname(), properties.port());
        
        return factory;
    }

    /**
     * 配置 SSL
     * 重写源文件: rabbitmq.js 第 111-119 行
     * 
     * 源码:
     * const connOptions = USE_SSL ? {
     *   ...amqpCred(),
     *   ...configureCA(RABBITMQ_CA),
     *   cert: RABBITMQ_CA_CERT,
     *   key: RABBITMQ_CA_KEY,
     *   pfx: RABBITMQ_CA_PFX,
     *   passphrase: RABBITMQ_CA_PASSPHRASE,
     *   rejectUnauthorized: RABBITMQ_REJECT_UNAUTHORIZED,
     * } : amqpCred();
     */
    private void configureSsl(CachingConnectionFactory factory, RabbitMQProperties properties) {
        try {
            com.rabbitmq.client.ConnectionFactory rabbitFactory = factory.getRabbitConnectionFactory();
            rabbitFactory.useSslProtocol(createSslContext(properties));
            log.info("[RABBITMQ] SSL configured successfully");
        } catch (Exception e) {
            log.error("[RABBITMQ] Failed to configure SSL: {}", e.getMessage());
            throw new RuntimeException("Failed to configure RabbitMQ SSL", e);
        }
    }

    /**
     * 创建 SSL 上下文
     * 重写源文件: rabbitmq.js 第 21-26 行
     * 
     * 源码:
     * const RABBITMQ_CA = (conf.get('rabbitmq:use_ssl_ca') ?? []).map((path) => loadCert(path));
     * const RABBITMQ_CA_CERT = readFileFromConfig('rabbitmq:use_ssl_cert');
     * const RABBITMQ_CA_KEY = readFileFromConfig('rabbitmq:use_ssl_key');
     * const RABBITMQ_CA_PFX = readFileFromConfig('rabbitmq:use_ssl_pfx');
     * const RABBITMQ_CA_PASSPHRASE = conf.get('rabbitmq:use_ssl_passphrase');
     */
    private SSLContext createSslContext(RabbitMQProperties properties) 
            throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException, java.security.KeyManagementException {
        
        SSLContext sslContext = SSLContext.getInstance("TLS");
        
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(null, null);
        
        List<String> caPaths = properties.useSslCa();
        if (caPaths != null && !caPaths.isEmpty()) {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            int certIndex = 0;
            for (String caPath : caPaths) {
                try (FileInputStream fis = new FileInputStream(caPath)) {
                    Certificate cert = certFactory.generateCertificate(fis);
                    trustStore.setCertificateEntry("rabbitmq-ca-" + certIndex++, cert);
                }
            }
        }
        
        trustManagerFactory.init(trustStore);
        
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        
        return sslContext;
    }

    /**
     * 创建 RabbitTemplate
     * 重写源文件: rabbitmq.js 第 158-163 行
     * 
     * 源码:
     * export const send = (exchangeName, routingKey, message) => {
     *   return amqpExecute(async (channel) => {
     *     const publish = util.promisify(channel.publish).bind(channel);
     *     return publish(exchangeName, routingKey, Buffer.from(message), { deliveryMode: 2 });
     *   });
     * };
     */
    @Bean
    @ConditionalOnMissingBean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setMandatory(true);
        
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("[RABBITMQ] Message delivery failed: {}", cause);
            }
        });
        
        template.setReturnsCallback(returned -> {
            log.warn("[RABBITMQ] Message returned: {}, replyCode: {}, replyText: {}", 
                    returned.getMessage(), 
                    returned.getReplyCode(), 
                    returned.getReplyText());
        });
        
        return template;
    }

    /**
     * 创建 RabbitAdmin
     */
    @Bean
    @ConditionalOnMissingBean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * JSON 消息转换器
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
