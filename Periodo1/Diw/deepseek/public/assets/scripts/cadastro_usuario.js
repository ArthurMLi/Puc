document.addEventListener('DOMContentLoaded', () => {
    const formCadastro = document.getElementById('form-cadastro');
    
    formCadastro.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Validar senhas
        const senha = document.getElementById('senha').value;
        const confirmarSenha = document.getElementById('confirmar-senha').value;
        
        if (senha !== confirmarSenha) {
            alert('As senhas não coincidem!');
            return;
        }
        
        const usuario = {
            nome: document.getElementById('nome').value,
            email: document.getElementById('email').value,
            login: document.getElementById('login').value,
            senha: senha,
            admin: false // Por padrão, novos usuários não são admin
        };
        
        try {
            // Verificar se usuário já existe
            const respostaVerificacao = await fetch(`http://localhost:3000/usuarios?login=${usuario.login}`);
            const usuariosExistentes = await respostaVerificacao.json();
            
            if (usuariosExistentes.length > 0) {
                alert('Nome de usuário já está em uso!');
                return;
            }
            
            // Criar novo usuário
            const respostaCadastro = await fetch('http://localhost:3000/usuarios', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(usuario)
            });
            
            if (respostaCadastro.ok) {
                alert('Cadastro realizado com sucesso!');
                window.location.href = 'login.html';
            } else {
                throw new Error('Erro ao cadastrar usuário');
            }
        } catch (erro) {
            console.error('Erro:', erro);
            alert('Erro ao cadastrar usuário');
        }
    });
});