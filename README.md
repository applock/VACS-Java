# VACS-Java
VACS

RabbitMq port for amqp - 5672

RabbitMq management console:
Enable it by editing file - C:\Users\...\RabbitMQ\enabled_plugins

Add following in this file -
[rabbitmq_federation_management,rabbitmq_management,rabbitmq_mqtt,rabbitmq_stomp].

Access it by - http://localhost:15672

Add new admin user for non-localhost access -
rabbitmqctl add_user vacs <PASSWORD>
rabbitmqctl set_user_tags vacs administrator
rabbitmqctl set_permissions -p / vacs ".*" ".*" ".*"