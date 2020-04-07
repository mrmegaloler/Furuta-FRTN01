%%% Futura pendulum model parameters (see Åkesson (1999)) (updated 2020-04-06)
  
l = 0.413;
M = 0.01;
Jp = 0.0009;
r = 0.235;
J = 0.05;
m = 0.02;
g = 9.81;

alfa = Jp+M*l^2;
beta = J+M*r^2+m*r^2;
gamma = M*r*l;
epsilon = l*g*(M+m/2);

friction = 0.2;  % Added viscous friction compared to Åkesson (1999)

%%% Models for control design, state vector x = [theta; thetaDot; phi; phiDot]

% Linear model for control in the upper position

A_upper = [-friction 1 0 0;
     (beta*epsilon)/(alfa*beta-gamma^2) 0 0 0;
     0 0 -friction 1;
     -(gamma*epsilon)/(alfa*beta-gamma^2) 0 0 0];
B_upper = [0 -gamma/(alfa*beta-gamma^2)*g 0 alfa/(alfa*beta-gamma^2)*g]';

% Linear model for control in lower position

A_lower = [-friction 1 0 0;
     -(beta*epsilon)/(alfa*beta-gamma^2) 0 0 0;
     0 0 -friction 1;
     -(gamma*epsilon)/(alfa*beta-gamma^2) 0 0 0];
B_lower = [0 gamma/(alfa*beta-gamma^2)*g 0 alfa/(alfa*beta-gamma^2)*g]';
