import react from 'react'

interface Props {
    children: react.ReactNode
}

interface State {
    hasError : boolean;
}

class ErrorBoundary extends react.Component<Props, State> {
    constructor (props : Props){
        super(props)
        this.state = {hasError: false }
    }

    static getDerivedStateFromError(error: Error){
        return {hasError : true}
    }

    componentDidCatch(error: Error, errorInfo: react.ErrorInfo): void {
        console.log (error, errorInfo)
    }

    render(): react.ReactNode {
        if (this.state.hasError)
             return <h1>Sorry :( ... Something went wrong</h1>
        else return this.props.children
    }
}

export default ErrorBoundary