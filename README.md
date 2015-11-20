# L.I.G.A.R

Padrões:

Porta que o Proxy escuta -> 24240
Separação das mensagens do cabeçalho -> ||

Mensagem do Servidor para o Proxy -> m0||porta||nomeServicoDisp0||nomeServicoDisp1||...||nomeServicoDispN
* exemplo: m0||24251||detran||facebook||subway

Mensagem do Cliente para o Proxy -> m1||nomeServicoQueQuer
* exemplo: m1||detran

Mensagem do Cliente para o Servidor -> m2||mensagem
* exemplo: m2||BKV2090
