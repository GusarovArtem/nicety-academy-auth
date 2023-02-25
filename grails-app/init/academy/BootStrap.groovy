package academy

class BootStrap {

    def init = { servletContext ->

        log.info 'BOOTSTRAPPING start'


        log.info 'BOOTSTRAPPING end'

    }

    def destroy = {
    }


}
