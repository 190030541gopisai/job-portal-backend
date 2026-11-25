def buildApp() {
    echo 'Building the code...'
    withCredentials([usernamePassword(credentials: '26ff7b4f-02e5-4807-ab0b-ae142d2a2c50', 
    passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        echo "$USER"
        echo "$PASS"
    }
}

def testApp() {
    echo 'Testing the code...'
}

def deployApp() {
    echo 'Deploying the code...'
    echo "Deploying version ${params.VERSION}"        
}

return this