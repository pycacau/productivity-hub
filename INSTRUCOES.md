# Instruções Rápidas

## Correção do pom.xml

Se encontrar algum erro ao compilar, verifique a linha 13 do `pom.xml`. 
A tag `<n>` deve ser corrigida para `<name>`:

```xml
<name>ProductivityHub</name>
```

## Como Executar

1. Certifique-se de ter Java 17+ instalado
2. Execute: `mvn clean compile javafx:run`

## Dependências JavaFX

Se você não tiver o JavaFX instalado separadamente, o Maven irá baixar automaticamente através das dependências configuradas.

