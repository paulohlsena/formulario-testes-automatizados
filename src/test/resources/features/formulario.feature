Feature: Cadastro de usuário no formulário

  Scenario: Cadastro com sucesso
    Given que o usuário acessa o formulário
    When ele preenche os dados corretamente
    And aceita os termos
    Then o sistema exibe a mensagem "Cadastro realizado com sucesso!"

  Scenario: Cadastro com menor de idade
    Given que o usuário acessa o formulário
    When ele preenche os dados com idade inferior a 18 anos
    And aceita os termos
    Then o sistema exibe a mensagem "Você precisa ter pelo menos 18 anos."

  Scenario: Cadastro com senhas diferentes
    Given que o usuário acessa o formulário
    When ele preenche senhas diferentes
    And aceita os termos
    Then o sistema exibe a mensagem "As senhas não coincidem."

  Scenario: Cadastro sem aceitar os termos
    Given que o usuário acessa o formulário
    When ele preenche os dados corretamente
    And não aceita os termos
    Then o sistema exibe a mensagem "Você deve aceitar os termos."
