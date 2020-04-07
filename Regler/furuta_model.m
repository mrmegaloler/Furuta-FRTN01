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

%declaration of q and r for lqr, alternative 2
Q = [20 0 0 0; 
    0 3 0 0;
    0 0 3 0;
    0 0 0 4]

R = 1*eye(1); 

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

%code for generating the lqr filter
[K,S,E] = lqrd(A_lower,B_lower,Q,R)

%code to check the poles generated
g = zpk(1,[E(1,1),E(2,1),E(3,1),E(4,1)],1,0.01);

pole(d2c(G))

%code for generating by pole placement, alternative 1

%[A,B] = c2d(A_lower,B_lower,0.01);

%p1_l = exp(-6*cosd(20)+6*sind(20)*i);
%p2_l = exp(-6*cosd(20)-6*sind(20)*i);
%p3_l = exp(-22);
%p4_l = exp(-1);

%[K, PREC,message] = place(A,B,[p1_l,p2_l,p3_l,p4_l])





