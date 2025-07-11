# Projeto Destaxa - Autorizador de Pagamentos

Este projeto é responsável por realizar a autorização de transações de pagamento utilizando **Spring Boot** e **jPOS 3**, com suporte a **Virtual Threads (Java 22)** para alta escalabilidade.

---

## ✅ Requisitos

- **Java 22** (versão mínima obrigatória) 
- **Projeto API** esta sendo exposto na porta 9000 

### 🔁 Exemplo de requisição

```bash
curl -X POST http://localhost:9000/authorization \
  -H "Content-Type: application/json" \
  -H "x-identifier: 324234324" \
  -d '{
    "external_id": "abc123",
    "value": 10000.0,
    "card_number": "4111111111111111",
    "installments": 1,
    "cvv": "123",
    "exp_month": 12,
    "exp_year": 25,
    "holder_name": "João da Silva"
  }'
  ```
---

## ⚠️ Importante

Este projeto **utiliza recursos exclusivos do Java 22**, incluindo **Virtual Threads** e **jPOS versão 3** otimizada.

**Executar com versões anteriores de Java resultará no seguinte erro:**

```text
has been compiled by a more recent version of the Java Runtime (class file version 66.0),
this version of the Java Runtime only recognizes class file versions up to 65.0
