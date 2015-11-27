## UPE - Caruaru
### L.I.G.A.R

Padrões:

Porta que o Proxy escuta -> 24240
Separação das mensagens do cabeçalho -> ||

Mensagem do Servidor para o Proxy -> M0||porta||nomeServicoDisp0||nomeServicoDisp1||...||nomeServicoDispN
* exemplo: M0||24251||detran||facebook||subway

Mensagem do Cliente para o Proxy -> M1||nomeServicoQueQuer
* exemplo: M1||detran

Mensagem do Cliente para o Servidor -> M2||mensagem
* exemplo: M2||BKV2090

Mensagem do Proxy para o Cliente -> Ip:porta||Ip:porta||...||Ip:porta
* exemplo: 192.168.0.1:24245||192.168.0.2:24245||...||192.168.0.3:24245
