def buildApp() {
    echo 'Building the code...'
}

def testApp() {
    echo 'Testing the code...'
}

def deployApp() {
    echo 'Deploying the code...'
    echo "Deploying version ${params.VERSION}"        
}

return this